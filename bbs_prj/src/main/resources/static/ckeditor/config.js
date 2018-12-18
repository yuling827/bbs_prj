/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';

    config.toolbarGroups = [
        // { name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
        // { name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
        { name: 'links' },
        { name: 'insert' },
        // { name: 'forms' },
        { name: 'tools' },
        { name: 'document',	   groups: [ 'mode', 'document', 'doctools' ] },
        { name: 'others' },{ name: 'colors' },{ name: 'basicstyles' },{name: 'list'},{name: 'indent'},
        // { name: 'others' },{ name: 'colors' },{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },{name: 'list'},{name: 'indent'},
        '/',
        { name: 'paragraph',   groups: [ 'blocks', 'align', 'bidi' ] },
        { name: 'styles' },

        // { name: 'about' }
    ];

    // Remove some buttons provided by the standard plugins, which are
    // not needed in the Standard(s) toolbar.
    //删除块中的某个按钮
    config.removeButtons = 'Underline,Subscript,Superscript,Anchor';

    //删除某个功能块
    config.removePlugins = "sourcearea,language,bidi,blockquote,div,save,newpage,preview,print,templates," +
		"flash,horizontalrule,pagebreak,iframe,showblocks";

    // Set the most common block elements.
    config.format_tags = 'p;h1;h2;h3;pre';

    // Simplify the dialog windows.
    config.removeDialogTabs = 'image:advanced;link:advanced';


	config.filebrowserUploadUrl="/files/upload/image"; //注意是配置在函数里边

};
