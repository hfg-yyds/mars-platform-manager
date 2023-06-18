package com.hacker.mars.application.api.tool;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hacker.mars.common.core.controller.BaseApi;

/**
 * swagger 接口
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/tool/swagger")
public class SwaggerApi extends BaseApi {

    @PreAuthorize("@ss.hasPermi('tool:swagger:view')")
    @GetMapping()
    public String index() {
        return redirect("/swagger-ui.html");
    }

}
