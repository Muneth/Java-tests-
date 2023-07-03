package com.atempo.tina.restapi.model.serverconfiguration;

import static com.atempo.tina.config.TNXMLServerConfiguration.TOMCAT_HTTPS_SCHEME;
import static com.atempo.tina.config.TNXMLServerConfiguration.TOMCAT_HTTP_SCHEME;

import com.atempo.tina.restapi.exception.InvalidDtoException;
import com.atempo.tina.restapi.model.CheckValidable;
import com.atempo.tina.restapi.model.InputDTO;
import com.atempo.tina.restapi.model.OutputDTO;
import com.atempo.tina.restapi.util.DtoValidator;
import com.atempo.tina.vo.TNTomcatConfiguration;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Pattern;

public class TomcatConfiguration implements CheckValidable, InputDTO, OutputDTO {
    public TomcatConfiguration() {
    }

    public TomcatConfiguration(TNTomcatConfiguration tnTomcatConfiguration) {
        this.httpPort = tnTomcatConfiguration.getHttpPort();
        this.httpProtocol = tnTomcatConfiguration.getHttpProtocol();
        this.serverVersion = tnTomcatConfiguration.getServerVersion();
        this.serverName = tnTomcatConfiguration.getServerName();
        this.fullProtocolName = tnTomcatConfiguration.getFullProtocolName();
        this.secure = tnTomcatConfiguration.getIsSecure();
        this.accessLogEnabled = tnTomcatConfiguration.getIsAccessLogEnabled();
    }

    public TNTomcatConfiguration updateTnTomcatConfiguration(TNTomcatConfiguration tntomcatConfiguration) {
        if (this.httpPort != null) {
            tntomcatConfiguration.setHttpPort(this.httpPort);
        }
        if (this.secure != null) {
            tntomcatConfiguration.setIsSecure(this.secure);
        }
        if (this.accessLogEnabled != null) {
            tntomcatConfiguration.setIsAccessLogEnabled(this.accessLogEnabled);
        }
        return tntomcatConfiguration;
    }

    static final String PORT_AS_STRING_REGEX =
            "^(?!0{2,})\\d{1,4}|^[1-5]\\d{4}|^6[0-4]\\d{3}|^65[0-4]\\d{2}|^655[0-2]\\d|^6553[0-5]$";

    @Schema(description = "Http port of Tomcat server between 0 and 65535")
    @Pattern(regexp = PORT_AS_STRING_REGEX,
            message = "HttpPort must have a value between 0 and 65535")
    public String getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(String httpPort) {
        this.httpPort = httpPort;
    }

    @Schema(description = "Http protocol of Tomcat server",
            allowableValues = { TOMCAT_HTTP_SCHEME, TOMCAT_HTTPS_SCHEME },
            defaultValue = TOMCAT_HTTPS_SCHEME)
    public String getHttpProtocol() {
        return httpProtocol;
    }

    public void setHttpProtocol(String httpProtocol) {
        this.httpProtocol = httpProtocol;
    }

    @Schema(description = "The version of Tomcat server",
            example = "9.0.71")
    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    @Schema(description = "The name of Tomcat server",
            example = "Apache Tomcat")
    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Schema(description = "The full protocol name of Tomcat server",
            example = "HTTP/1.1")
    public String getFullProtocolName() {
        return fullProtocolName;
    }

    public void setFullProtocolName(String fullProtocolName) {
        this.fullProtocolName = fullProtocolName;
    }

    @Schema(description = "Indicate if the server is secure")
    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    @Schema(description = "Indicate if the access log is enabled")
    public Boolean getAccessLogEnabled() {
        return accessLogEnabled;
    }

    public void setAccessLogEnabled(Boolean accessLogEnabled) {
        this.accessLogEnabled = accessLogEnabled;
    }

    public void checkValidity() throws InvalidDtoException {

        DtoValidator.validate(this);
    }

    private String httpPort = null;
    private String httpProtocol = null;
    private String serverVersion = null;
    private String serverName = null;
    private String fullProtocolName = null;
    private Boolean secure = null;
    private Boolean accessLogEnabled = null;
}
