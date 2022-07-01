/**
 * PrimeFaces FileUpload Widget
 * Correction de la méthode parseIframeContent qui ne fonctionne pas sur IE10
 */
PrimeFaces.widget.FileUpload = PrimeFaces.widget.BaseWidget.extend({
    init: function(cfg) {
        this._super(cfg);
        
        this.form = this.jq.parents('form:first');
        this.buttonBar = this.jq.children('.fileupload-buttonbar');
        this.uploadContent = this.jq.children('.fileupload-content');

        var _self = this;

        //upload template
        this.cfg.uploadTemplate = '<tr class="template-upload{{if error}} ui-state-error{{/if}}">' + 
                                '<td class="preview"></td>' +
                                '<td class="name">$' + '{name}</td>' +
                                '<td class="size">$' + '{sizef}</td>' + 
                                '{{if error}}' + 
                                '<td class="error" colspan="2">' + 
                                        '{{if error === "maxFileSize"}}' + this.getMessage(this.cfg.invalidSizeMessage, this.INVALID_SIZE_MESSAGE) +
                                        '{{else error === "minFileSize"}}'+ this.getMessage(this.cfg.invalidSizeMessage, this.INVALID_SIZE_MESSAGE)  +
                                        '{{else error === "acceptFileTypes"}}'+ this.getMessage(this.cfg.invalidFileMessage, this.INVALID_FILE_MESSAGE)  +
                                        '{{else error === "maxNumberOfFiles"}}' + this.getMessage(this.cfg.fileLimitMessage, this.FILE_LIMIT_MESSAGE) +
                                        '{{else}}$' + '{error}' +
                                        '{{/if}}' +
                                '</td>' +
                                '{{else}}' +
                                '<td class="progress"><div></div></td>' +
                                '<td class="start"><button class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only">' +
                                '<span class="ui-button-icon-left ui-icon ui-icon ui-icon-arrowreturnthick-1-n"></span>' +
                                '<span class="ui-button-text">ui-button</span></button></td>' +
                                '<td class="cancel"><button class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only">' +
                                '<span class="ui-button-icon-left ui-icon ui-icon ui-icon-cancel"></span>' +
                                '<span class="ui-button-text">ui-button</span></button></td>' +
                                '{{/if}}' +
                                '</tr>';

        //download template
        this.cfg.downloadTemplate = '';

        //config
        this.cfg.fileInput = $(PrimeFaces.escapeClientId(this.id + '_input'));
        this.cfg.paramName = this.id;
        this.cfg.sequentialUploads = true;
        this.cfg.dataType = 'xml';
        this.cfg.namespace = this.jqId;
        this.cfg.disabled = this.cfg.fileInput.is(':disabled');

        //iframe content parser
        $.ajaxSetup({
            converters: {
                'iframe xml': this.parseIFrameResponse
            }
        });

        //params
        this.cfg.formData = function() {
            return _self.createPostData();
        };

        //dragdrop
        this.cfg.dropZone = this.cfg.dnd && !this.cfg.disabled ? this.jq : null;

        //create widget
        if(this.form.data().fileupload) {
            this.destroy();
        }

        this.form.fileupload(this.cfg).bind('fileuploaddone', function(e, data) {
            PrimeFaces.ajax.AjaxResponse(data.result);

            if(_self.cfg.oncomplete) {
                _self.cfg.oncomplete.call(_self);
            }
        });

        //disable buttonbar
        if(this.cfg.disabled) {
            this.disable();
        }

        //show the UI
        this.jq.show();

        //bind events
        PrimeFaces.skinButton(this.buttonBar.children('.ui-button'));

        this.buttonBar.children('.start.ui-button').click(function(e) {
            _self.uploadContent.find('.start button').click();
        });

        this.buttonBar.children('.cancel.ui-button').click(function(e) {
        	_self.clearMessages();
        });
    },
    
    /*
     * Correction Primefacaes 3.2
     * Les éléments invalides ne sont pas effacés
     */
    clearMessages: function() {
    	this.uploadContent.find('tr.template-upload').remove();
    },
    
    parseIFrameResponse: function(iframe) {
        var response = iframe.contents(),
        responseText = null;

        //Somehow firefox, opera, ie9 ignores xml root element so we add it ourselves
        if( $.browser.mozilla || $.browser.opera || ($.browser.msie && ($.browser.version == '9.0' || $.browser.version == '10.0'))) {
            responseText = '<?xml version="1.0" encoding="UTF-8"?><partial-response><changes>';
            response.find('update').each(function(i, item) {
                var update = $(item),
                id = update.attr('id'),
                content = '<![CDATA[' + update.text() + ']]>';

                responseText += '<update id="' + id + '">' + content + '</update>';
            });
            responseText += '</changes></partial-response>';
        } 
        //IE
        else {
            responseText = $.trim(iframe.contents().text().replace(/(> -)|(>-)/g,'>'));
        }

        return $.parseXML(responseText);
    },
    
    createPostData: function() {
        var process = this.cfg.process ? this.id + ' ' + this.cfg.process : this.id,
        params = this.form.serializeArray();

        params.push({name: PrimeFaces.PARTIAL_REQUEST_PARAM, value: 'true'});
        params.push({name: PrimeFaces.PARTIAL_PROCESS_PARAM, value: process});
        params.push({name: PrimeFaces.PARTIAL_SOURCE_PARAM, value: this.id});

        if(this.cfg.update) {
            params.push({name: PrimeFaces.PARTIAL_UPDATE_PARAM, value: this.cfg.update});
        }

        return params;
    },
    
    getMessage: function(customMsg, defaultMsg) {
        return customMsg || defaultMsg; 
    },
    
    destroy: function() {
        this.form.fileupload('destroy');
    },
    
    disable: function() {
        this.jq.children('.fileupload-buttonbar').find('.ui-button')
                            .addClass('ui-state-disabled')
                            .unbind()
                            .bind('click', function(e) {e.preventDefault();});

        this.cfg.fileInput.css('cursor', 'auto');
    },
    
    INVALID_SIZE_MESSAGE: 'Invalid file size.',
    
    INVALID_FILE_MESSAGE: 'Invalid file type.',
    
    FILE_LIMIT_MESSAGE: 'Max number of files exceeded.'
    
});

/**
	Version javascript de la mantis 0000460
	Parcourt des widgets de chaque page afin de mettre un événement sur les champs de filtrage
	Gros inconvéninent, le script ne se lance qu'une fois la fenêtre complétement chargée et peut donc
	n'être exécuté qu'après quelques secondes...
*/
/*
if( document.documentMode === 10 ) {
	// on attend que tout soit chargé, surtout les scripts dynamiques Primefaces
	jQuery(window).bind("load", function() {
		if( window.console && console.log ) {
			console.log('Document fully loaded');
		}
		var widgets = {};
		// les widgets Primefaces sont sauvegardés dans l'objet windows
		// on parcourt les propriétés de l'objet pour trouver les widgets
		for( var w in window ) {
			if( window[w] instanceof PrimeFaces.widget.DataTable ) {
				widgets[window[w].id] = window[w];
	
				// gestion de l'événement mouseup sur les champs de filtrage
				window[w].getJQ().find('.ui-filter-column .ui-column-filter.ui-inputtext').mouseup(function(evt) {
					addTimeout($(this), '.ui-datatable', widgets, function() {
						this.filter();
					});
				});
			} else if(window[w] instanceof PrimeFaces.widget.PickList ) {
				widgets[window[w].id] = window[w];
	
				window[w].sourceFilter.mouseup(function(evt) {
					addTimeout($(this), '.ui-picklist', widgets, function() {
						this.filter('', this.sourceList);
					});
				});	
				window[w].targetFilter.mouseup(function(evt) {
					addTimeout($(this), '.ui-picklist', widgets, function() {
						this.filter('', this.targetList);
					});
				});	
			} else if(window[w] instanceof PrimeFaces.widget.AutoComplete ) {
				widgets[window[w].id] = window[w];
	
				window[w].input.mouseup(function(evt) {
					addTimeout($(this), '.ui-autocomplete', widgets, function() {
						this.hide();
					});
				});	
			}
		}
	});
}
*/