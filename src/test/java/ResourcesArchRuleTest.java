import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.freeze.FreezingArchRule;
import java.util.Map;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static sun.tools.jconsole.Messages.ARCHITECTURE;

@Tag(ARCHITECTURE)
class ResourcesArchRuleTest {
    private static final String PACKAGE_TESTAUTO_RESOURCES = null;
    private static final String PACKAGE_RESOURCES = null;
    private final JavaClasses classesResourcesTest = new ClassFileImporter().importPackages(PACKAGE_TESTAUTO_RESOURCES);
    private final JavaClasses classesResources = new ClassFileImporter().importPackages(PACKAGE_RESOURCES);

    /**
     * Rule : all resources have an automatic test
     */
    @Test
    void resourcesHaveAWebfluxTestAssociated() {
        ArchCondition<JavaMethod> haveCorrespondingTest = new ArchCondition<JavaMethod>("haveCorrespondingTest") {
            @Override
            public void check(JavaMethod item,
                              ConditionEvents events) {
                boolean isOK = false;
                String classTest = item.getOwner().getSimpleName() + "Test";
                for (JavaClass test : classesResourcesTest) {
                    if (classTest.equals(test.getSimpleName())) {
                        for (JavaMethod method : test.getMethods()) {
                            if (method.getName().equals(item.getName())) {
                                isOK = true;
                                break;
                            }
                        }
                    }
                }
                if (!isOK) {
                    String message =
                            String.format("Method %s in (%s.java:1) should have a corresponding  test method ", item.getFullName(),
                                    item.getOwner().getName());
                    events.add(SimpleConditionEvent.violated(item, message));
                }
            }
        };

        ArchRule frozenRule = FreezingArchRule.freeze(
                ArchRuleDefinition.methods().that().arePublic().and().areDeclaredInClassesThat()
                        .doNotHaveSimpleName("TestResource").and().areDeclaredInClassesThat()
                        .resideInAPackage(PACKAGE_RESOURCES).should(haveCorrespondingTest));
        frozenRule.check(classesResources);

    }

    /**
     * Method: to verify that the classes implement OutputDTO interface or ignore it
     */

    private void checkClassImplementOutputDTOInputDTOorIsIgnored(JavaClass javaClass,
                                                                 JavaMethod javaMethod,
                                                                 ConditionEvents conditionEvents,
                                                                 String dto,
                                                                 String interfaceFullName) {
        if (!VOID_JAVA_CLASS.equals(javaClass.getName()) && !STRING_JAVA_CLASS.equals(javaClass.getName()) &&
                !HTTP_HEADERS_CLASS.equals(javaClass.getName())) {

            if (!doesClassOrParentImplements(javaClass, interfaceFullName)) {
                String message =
                        String.format("Class %s  should have " + dto + " interface implemented with Method %s in (%s.java:1)",
                                javaClass.getName(), javaMethod.getFullName(), javaMethod.getOwner().getName());
                conditionEvents.add(SimpleConditionEvent.violated(javaMethod, message));
            }
        }
    }

    /**
     * Method: to verify the properties of the annotations and find the classes for InputDTO implementations.
     */
    private void checkAnnotationPropertiesToSearchForDTOType(Map<String, Object> properties,
                                                             JavaMethod javaMethod,
                                                             ConditionEvents conditionEvents,
                                                             String dtoType,
                                                             String interfaceFullName) {
        for (Map.Entry<String, Object> entry1 : properties.entrySet()) {
            if (entry1.getKey().equals("schema")) {
                if (entry1.getValue() instanceof JavaAnnotation) {
                    JavaAnnotation anno3 = ((JavaAnnotation) entry1.getValue());
                    Map<String, Object> properties3 = anno3.getProperties();
                    {
                        for (Map.Entry<String, Object> entry2 : properties3.entrySet()) {
                            if (entry2.getKey().equals("oneOf")) {
                                if (entry2.getValue() instanceof JavaClass[]) {
                                    JavaClass[] classesAnno = ((JavaClass[]) entry2.getValue());
                                    for (JavaClass javaClass : classesAnno) {
                                        checkClassImplementOutputDTOInputDTOorIsIgnored(javaClass, javaMethod, conditionEvents, dtoType,
                                                interfaceFullName);
                                    }
                                }
                            } else if (entry2.getKey().equals("implementation")) {
                                if (entry2.getValue() instanceof JavaClass) {
                                    JavaClass javaClass = ((JavaClass) entry2.getValue());
                                    checkClassImplementOutputDTOInputDTOorIsIgnored(javaClass, javaMethod, conditionEvents, dtoType,
                                            interfaceFullName);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Method: to verify the properties of the annotations and find the classes for OutputDTO implementations.
     */

    private void checkAnnotationPropertiesToSearchForOutputDTOorInputDTO(Map<String, Object> properties,
                                                                         JavaMethod javaMethod,
                                                                         ConditionEvents conditionEvents,
                                                                         String dtoType,
                                                                         String interfaceFullName) {
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            if (entry.getKey().equals("content")) {
                if (entry.getValue() instanceof JavaAnnotation[]) {
                    JavaAnnotation[] anno = ((JavaAnnotation[]) entry.getValue());
                    for (JavaAnnotation anno2 : anno) {
                        Map<String, Object> properties2 = anno2.getProperties();
                        for (Map.Entry<String, Object> entryy : properties2.entrySet()) {
                            if (("array").equals(entryy.getKey())) {
                                if (entryy.getValue() != null) {
                                    if (entryy.getValue() instanceof JavaAnnotation) {
                                        JavaAnnotation annotation1 = ((JavaAnnotation) entryy.getValue());
                                        Map<String, Object> propertiess = annotation1.getProperties();
                                        checkAnnotationPropertiesToSearchForDTOType(propertiess, javaMethod, conditionEvents, dtoType,
                                                interfaceFullName);
                                    }
                                }
                            } else {
                                checkAnnotationPropertiesToSearchForDTOType(properties2, javaMethod, conditionEvents, dtoType,
                                        interfaceFullName);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Rule : This rule check that classes use in resource method as api response are all implementing OutputDTO.
     * This rule analyse @ApiResponse annotation of different form:
     * - @ApiResponse - single annotation declared
     * - @ApiResponses[] - Array of multiple annotations
     */
    @Test
    void resourcesShouldVerifyOutputDtoType() {
        ArchCondition<JavaMethod> haveDtoType = new ArchCondition<JavaMethod>("haveOutputDTOType") {
            @Override
            public void check(JavaMethod javaMethod,
                              ConditionEvents conditionEvents) {

//    When there are multiple @ApiResponse annotations are written, they automatically added in @ApiResponses[] that's why we need this case.
//
                for (JavaAnnotation<JavaMethod> annotation : javaMethod.getAnnotations()) {
                    if (API_RESPONSE.equals(annotation.getRawType().getName())) {
                        Map<String, Object> properties = annotation.getProperties();

                        checkAnnotationPropertiesToSearchForOutputDTOorInputDTO(properties, javaMethod, conditionEvents,
                                "OutputDTO", OUTPUT_DTO);
                    } else if (API_RESPONSES.equals(annotation.getRawType().getName())) {
                        Map<String, Object> apiResponses = annotation.getProperties();
                        for (Map.Entry<String, Object> apiResponse : apiResponses.entrySet()) {
                            if (apiResponse.getValue() instanceof JavaAnnotation[]) {
                                JavaAnnotation[] apiProperties = ((JavaAnnotation[]) apiResponse.getValue());
                                for (JavaAnnotation apiAnno : apiProperties) {
                                    Map<String, Object> properties = apiAnno.getProperties();
                                    checkAnnotationPropertiesToSearchForOutputDTOorInputDTO(properties, javaMethod, conditionEvents,
                                            "OutputDTO", OUTPUT_DTO);
                                }
                            }
                        }
                    }
                }
            }
        };

        ArchRule frozenRule = FreezingArchRule.freeze(
                ArchRuleDefinition.methods().that().arePublic().and().areDeclaredInClassesThat()
                        .resideInAPackage(PACKAGE_RESOURCES).should(haveDtoType));
        frozenRule.check(classesResources);
    }


    /**
     * Rule : This rule check that classes use in resource method as api response are all implementing InputDTO.
     */
    @Test
    void resourcesShouldVerifyInputDtoType() {
        ArchCondition<JavaMethod> haveInputDtoType = new ArchCondition<JavaMethod>("haveInputDTOType") {
            @Override
            public void check(JavaMethod javaMethod,
                              ConditionEvents conditionEvents) {

                for (JavaAnnotation<JavaMethod> annotation : javaMethod.getAnnotations()) {
                    if (REQUEST_BODY.equals(annotation.getRawType().getName())) {
                        Map<String, Object> properties = annotation.getProperties();

                        checkAnnotationPropertiesToSearchForOutputDTOorInputDTO(properties, javaMethod, conditionEvents, "InputDTO",
                                INPUT_DTO);
                    }
                }
            }
        };
        ArchRule frozenRule = FreezingArchRule.freeze(
                ArchRuleDefinition.methods().that().arePublic().and().areDeclaredInClassesThat()
                        .resideInAPackage(PACKAGE_RESOURCES).should(haveInputDtoType));
        frozenRule.check(classesResources);
    }
}