/**
 * @license Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function (config) {
    // Define changes to default configuration here. For example:
    // config.language = 'fr';
    // config.uiColor = '#AADC6E';
    config.language = 'zh-cn';
    // 设置宽高
    // config.width = 400;
    //改变大小的最小宽度
    config.resize_minWidth = 400;
    config.height = 800;
    // 工具栏（基础'Basic'、全能'Full'、自定义）plugins/toolbar/plugin.js
    config.filebrowserImageUploadUrl = "/uploadImageByCK?CKEditorFuncNum=2";
    config.toolbar = 'Full';
    config.toolbar_Full = [
        {name: 'document', items: ['Source', '-', 'DocProps', 'Preview', 'Print', '-']},
        {name: 'clipboard', items: ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo']},
        {name: 'editing', items: ['Find', 'Replace', '-']},
        {
            name: 'basicstyles',
            items: ['Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat','-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock','TextColor', 'BGColor','Image']
        },
        {
            name: 'paragraph',
            items: ['Styles', 'Format', 'Font', 'FontSize','NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv',  '-', 'BidiLtr', 'BidiRtl']
        },
        {name: 'links', items: ['Link', 'Unlink', 'Anchor', 'Flash', 'Table', 'HorizontalRule']},
    ];
    config.toolbar_Basic = [['Bold', 'Italic', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink', '-', 'About']];
    config.removeDialogTabs = 'image:advanced;link:advanced';
    config.image_previewText = ' ';
};
