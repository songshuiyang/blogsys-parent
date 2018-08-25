seajs.config({
    base: './static', /*base默认路径是seajs的父路径,现在配置了所以是相对于index.html路径*/
    alias: {
        'jquery': 'js/jquery.js', /*需要包装jquery*/
        'utils': 'js/commonUtils.js',
        'main': 'js/main.js',
        'bootstrap': 'plugins/bootstrap/bootstrap-3.3.0/js/bootstrap.min.js',
        'waves': 'plugins/waves-0.7.5/waves.min.js',
        'malihu-scrollvar': 'plugins/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js',
        'bootstrapMenu': 'plugins/BootstrapMenu.min.js',
        'device': 'plugins/device.min.js',
        'fullPage': 'plugins/fullPage/jquery.fullPage.min.js',
        'jdirk': 'plugins/fullPage/jquery.jdirk.min.js',
        'jquery-coojie': 'plugins/jquery.cookie.js',
        'index': 'js/index.js',
        'bootstrapTable': 'plugins/bootstrap/bootstrap-table-1.11.0/bootstrap-table.min.js'
    }
});