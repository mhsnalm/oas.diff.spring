package mhsnalm.com.oas.diff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openapitools.openapidiff.core.OpenApiCompare;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.openapitools.openapidiff.core.output.JsonRender;
import org.openapitools.openapidiff.core.output.MarkdownRender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.models.auth.AuthorizationValue;


@RestController
public class Controller {

    @GetMapping("/heartbeat")
    public String heartBeat() {
        return "The heart is beating ...";
    }

    @PostMapping("/diff")
    public String Diff(@RequestParam(value = "source") String source, 
                        @RequestParam(value = "target") String target,
                        @RequestParam(value = "diff-return-type", defaultValue = "JSON") String diffReturnType,
                        @RequestParam(value = "source-auth-key", defaultValue = "") String sourceAuthKey,
                        @RequestParam(value = "source-auth-value", defaultValue = "") String sourceAuthValue,
                        @RequestParam(value = "source-auth-type", defaultValue = "") String sourceAuthType,
                        @RequestParam(value = "target-auth-key", defaultValue = "") String targetAuthKey,
                        @RequestParam(value = "target-auth-value", defaultValue = "") String targetAuthValue,
                        @RequestParam(value = "target-auth-type", defaultValue = "") String targetAuthType ) 
    { 
        io.swagger.v3.parser.core.models.AuthorizationValue authValueBase = new io.swagger.v3.parser.core.models.AuthorizationValue(sourceAuthKey,sourceAuthValue,sourceAuthType);  
        io.swagger.v3.parser.core.models.AuthorizationValue authValueRelease = new io.swagger.v3.parser.core.models.AuthorizationValue(targetAuthKey,targetAuthValue,targetAuthType);
        List<io.swagger.v3.parser.core.models.AuthorizationValue> authList = new ArrayList<io.swagger.v3.parser.core.models.AuthorizationValue>();
        authList.add(authValueBase);
        authList.add(authValueRelease);
        
        ChangedOpenApi diffResult = OpenApiCompare.fromLocations(source, target,authList);

        if(diffReturnType.equals("MD"))
            return new MarkdownRender().render(diffResult);
        else
            return new JsonRender().render(diffResult);
    }

}
