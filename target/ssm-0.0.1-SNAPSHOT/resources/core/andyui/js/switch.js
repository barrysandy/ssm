/*
fwq 2016/3/21
switch 组件：
若在input上使用disabled也可以禁用开关
changeBefor 状态改变前，传递checkbox对象作为回掉参数
changeAfter 状态改变后，传递checkbox对象作为回掉参数
setEnable(true/false) 是否禁用开关
setState(true/false)  设置开关状态，开启||关闭
 */
;
( function ( $ ) {

	$.fn.extend( {

		switchs: function () {
			/**
			 * 控制开关样式
			 * @method stateInit
			 * @param  {[Object]}  $element [JQuery对象]
			 * @param  {[Booleam]}  flag
			 * @return {[void]}
			 */

			var stateInit = function ( $element, flag ) {
				var _icon, stateClass;
				$element.find( 'i' )
					.remove();
				$element.removeClass( 'on off' );
				flag ? _icon = '<i class="iconfont ">&#xe61c;</i>' : _icon = '<i class="iconfont ">&#xe61d;</i>';
				flag ? stateClass = 'on' : stateClass = 'off';
				$element.addClass( stateClass )
					.append( _icon )
					.addClass( 'switchState' );
			};
			Config = {
				/**
				 * 方法主程
				 * @method init
				 * @param  {[Object]} option [外部传入的配置]
				 * @return {[void]}
				 */
				init: function ( option ) {
					var defaults = {
						changeBefor: function ( $element ) {

						},
						changeAfter: function ( $element ) {

						}
					};

					var
						$element = $( this ),
						$input = $element.children( 'input' ),
						flag = $input.is( ':checked' ) ? true : false;
					if ( !$element.hasClass( 'switchState' ) ) {
						stateInit( $element, flag );
					}
					var options = $.extend( true, defaults, option || {} );

					$element.unbind( 'click' );
					$element.on( 'click', function ( event ) {
						event.stopPropagation();
						options.changeBefor( $input );

						if ( $input.prop( "disabled" ) ) return;

						var flag = $input.is( ":checked" );

						$input.prop( 'checked', !flag );
						stateInit( $(this), !flag );
						options.changeAfter( $input );
					} );
				},
				/**
				 * 是否禁用
				 * @method setEnable
				 */
				setEnable: function () {
					$( this )
						.children( 'input' )
						.prop( 'disabled', arguments[ 1 ] );
				},
				/**
				 * 开关状态
				 * @method setState
				 */
				setState: function () {
					stateInit( $( this ), arguments[ 1 ] );
					$( this )
						.children( 'input' )
						.prop( 'checked', arguments[ 1 ] );
				}
			}

			var method = arguments[ 0 ];
			if ( Config[ method ] ) {
				method = Config[ method ];
			} else if ( typeof ( method ) == 'object' || !method ) {
				method = Config.init;
			} else {
				$.error( "Something bad happened" );
				return this;
			}
			return method.apply( this, arguments );
		}
	} )
} )( jQuery );
