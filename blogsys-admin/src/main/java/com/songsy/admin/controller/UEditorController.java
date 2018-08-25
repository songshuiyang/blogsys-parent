package com.songsy.admin.controller;


import com.songsy.base.BaseController;
import com.songsy.core.ueditor.ConfigManager;
import com.songsy.core.ueditor.define.ActionMap;
import com.songsy.core.ueditor.define.AppInfo;
import com.songsy.core.ueditor.define.BaseState;
import com.songsy.core.ueditor.define.State;
import com.songsy.core.ueditor.hunter.FileManager;
import com.songsy.core.ueditor.hunter.ImageHunter;
import com.songsy.core.ueditor.upload.Uploader;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Ueditor 后台统一接口
 * @author songshuiyang
 * @date 2018/3/4 18:11
 */
@Controller
@RequestMapping("/ueditor")
public class UEditorController extends BaseController {

    private HttpServletRequest request = null;

    private String actionType = null;

    private ConfigManager configManager = null;


    @RequestMapping(value = "ueditorAction", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JSONObject exec (@RequestParam String action, HttpServletRequest request) {
        String result;
        this.actionType = action;
        this.request = request;
        String rootPath =  request.getSession().getServletContext().getRealPath("/");
        String contextPath = request.getContextPath();
        this.configManager = ConfigManager.getInstance( rootPath, contextPath,"/static/plugins/ueditor/jsp/controller.jsp");

        String callbackName = this.request.getParameter("callback");

        if ( callbackName != null ) {
            result =  !validCallbackName( callbackName ) ? new BaseState( false, AppInfo.ILLEGAL ).toJSONString() : callbackName+"("+this.invoke()+");";
        } else {
            result = this.invoke();
        }
        return JSONObject.fromObject(result);

    }

    public String invoke() {

        if ( actionType == null || !ActionMap.mapping.containsKey( actionType ) ) {
            return new BaseState( false, AppInfo.INVALID_ACTION ).toJSONString();
        }

        if ( this.configManager == null || !this.configManager.valid() ) {
            return new BaseState( false, AppInfo.CONFIG_ERROR ).toJSONString();
        }

        State state = null;

        int actionCode = ActionMap.getType( this.actionType );

        Map<String, Object> conf;

        switch ( actionCode ) {

            case ActionMap.CONFIG:
                return this.configManager.getAllConfig().toString();

            case ActionMap.UPLOAD_IMAGE:
            case ActionMap.UPLOAD_SCRAWL:
            case ActionMap.UPLOAD_VIDEO:
            case ActionMap.UPLOAD_FILE:
                conf = this.configManager.getConfig( actionCode );
                state = new Uploader( request, conf ).doExec();
                break;

            case ActionMap.CATCH_IMAGE:
                conf = configManager.getConfig( actionCode );
                String[] list = this.request.getParameterValues( (String)conf.get( "fieldName" ) );
                state = new ImageHunter( conf ).capture( list );
                break;

            case ActionMap.LIST_IMAGE:
            case ActionMap.LIST_FILE:
                conf = configManager.getConfig( actionCode );
                int start = this.getStartIndex();
                state = new FileManager( conf ).listFile( start );
                break;

        }

        assert state != null;
        return state.toJSONString();

    }

    private int getStartIndex () {

        String start = this.request.getParameter( "start" );

        try {
            return Integer.parseInt( start );
        } catch ( Exception e ) {
            return 0;
        }

    }

    /**
     * callback参数验证
     * @param name 名字
     * @return boolean
     */
    private boolean validCallbackName ( String name ) {
        return name.matches( "^[a-zA-Z_]+[\\w0-9_]*$" );
    }
}
