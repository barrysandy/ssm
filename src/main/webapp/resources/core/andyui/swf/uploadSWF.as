package 
{
	import flash.display.MovieClip;
	import flash.events.*;
	import flash.external.ExternalInterface;
	import flash.net.FileReference;
	import flash.net.URLRequest;
	import flash.net.FileFilter;
	import flash.system.Security;
	import flash.net.FileReferenceList;

	flash.system.Security.allowDomain("*");
	flash.system.Security.allowInsecureDomain("*");

	//import CustomFileReferenceList;

	public class uploadSWF extends MovieClip
	{
		private var fr:FileReference = new FileReference();
		private var fileRef:FileReferenceList = new FileReferenceList();
		private var ul:URLRequest = new URLRequest();
		private var pendingFiles:Array;
		private var fileTypes:Array;
		private var isAvailable:Boolean = ExternalInterface.available;//是否允许文件上传
		private var isContinue:Boolean = true;//流程是否继续
		private var rootFiles = root.loaderInfo.parameters["files"];//是否允许多文件上传
		private var rootUpLoadUrl = root.loaderInfo.parameters["uploadUrl"];//外部对象上传地址
		private var rootUpLoadTemporaryUrl = root.loaderInfo.parameters["uploadTemporaryUrl"];//外部对象 临时上传地址
		private var rootXMLUrl = root.loaderInfo.parameters["xmlUrl"];//xml文件路径配置
		private var upload_id = root.loaderInfo.parameters["uploadId"];//外部JS对象id
		private var rootData = root.loaderInfo.parameters["data"];//外部需要POST的 data
		private var rootFileNameData = root.loaderInfo.parameters["uploadDataFieldName"];//上传文件 文件数据 头属性名
		private var rootFileType = root.loaderInfo.parameters["fileType"];//上传文件类型字段

		public function uploadSWF()
		{
			//Security.loadPolicyFile("http://172.16.123.153:8080/photo");
			if (rootXMLUrl)
			{
				flash.system.Security.loadPolicyFile(rootXMLUrl);
			}

			//ul.url = "http://172.16.123.239:8080/photo/FileUploadServlet";
			//rootFiles = "false";
			//rootFileNameData = "Filedata";
			//rootUpLoadUrl = "http://172.16.116.212/ad.php";
			//rootUpLoadUrl = "http://172.16.116.218:8080/eimm_web/fileobject/upload1.dhtml?bizType=EnpProduce&bizId=0b349a6cadfb4379a1cae12f75985213";
			//rootFiles = false;
			
			//文件类型
			if (rootFileType)
			{
				var arr = [];
				fileTypes = [];
				arr = rootFileType.split(":");
				var filter:FileFilter = new FileFilter(arr[0],arr[1]);
				fileTypes.push(filter);
			}

			//验证上载路径
			if (rootUpLoadUrl)
			{
				ul.url = rootUpLoadUrl;
				ul.data = rootData;
			}
			else
			{
				openBtn.visible = false;
				isContinue = false;
			}

			if (rootUpLoadTemporaryUrl && rootFiles == "true")
			{
				ul.url = rootUpLoadTemporaryUrl;
				ul.data = rootData;
			}
			//openBtn 为触发按钮 舞台上 定义一个命名为openBtn的按钮就可以了
			openBtn.addEventListener(MouseEvent.CLICK, openFile);
			//this.loaderInfo.addEventListener(Event.COMPLETE, loaded);

			//function loaded()
//			{
//				if (framesLoaded==totalFrames)
//				{
//					if (ExternalInterface.available)
//					{
//						trace("touchf");
//						ExternalInterface.addCallback("touchf",openFile);
//					}
//				}
//
//			}
		}

		private function openFile(e:Event):void
		{
			if (rootFiles == "false")
			{
				//单文件引用
				fr.browse(fileTypes);
				fr.addEventListener(Event.SELECT,selectHandler);
			}
			else
			{
				//多文件引用
				//fileRef.addEventListener(FileReferenceListExample.LIST_COMPLETE, listCompleteHandler);
				fileRef.browse(fileTypes);
				fileRef.addEventListener(Event.SELECT, selectFilesHandler);
			}
		}

		//------------------------------------------多文件操作

		private function addPendingFile(file:FileReference):void
		{
			trace("addPendingFile: name=" + file.name);
			pendingFiles.push(file);
			//file.addEventListener(Event.OPEN, openHandler);
			file.addEventListener(Event.COMPLETE, completeHandler);
			//完成返回数据
			file.addEventListener(DataEvent.UPLOAD_COMPLETE_DATA, completeDataHandler);
			//file.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
			file.addEventListener(ProgressEvent.PROGRESS, progressFilesHandler);
			//file.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
			file.upload(ul, rootFileNameData);
		}

		private function selectFilesHandler(e:Event):void
		{
			pendingFiles = new Array();
			var file:FileReference;
			for (var i:uint = 0; i < fileRef.fileList.length; i++)
			{
				file = FileReference(fileRef.fileList[i]);
				//file.index = i;
				addPendingFile(file);
				//通知JS 显示图片状态 已经选择对象
				ExternalInterface.call(upload_id+'_showFun', file);
			}
		}

		private function progressFilesHandler(e:ProgressEvent):void
		{
			var num:uint = (e.bytesLoaded / e.bytesTotal) * 100;
			ExternalInterface.call(upload_id + '_progress',e.bytesLoaded,e.bytesTotal, num, e.target);
		}

		private function removePendingFile(file:FileReference):void
		{
			for (var i:uint; i < pendingFiles.length; i++)
			{
				if (pendingFiles[i].name == file.name)
				{
					pendingFiles.splice(i, 1);
					if (pendingFiles.length == 0)
					{
						//doOnComplete();
						//trace("uploadListComplete");
						//多文件上传完毕 通知JS
						ExternalInterface.call(upload_id+'_uploadListComplete',true);
					}
					return;
				}
			}
		}

		//------------------------------------------单文件操作

		private function selectHandler(e:Event):void
		{
			fr.upload(ul, rootFileNameData);
			//通知JS 显示图片状态 已经选择对象
			ExternalInterface.call(upload_id+'_showFun', e.target);
			fr.addEventListener(ProgressEvent.PROGRESS,progressHandler);
			//上载完成事件
			fr.addEventListener(Event.COMPLETE,completeHandler);
			//完成返回数据
			fr.addEventListener(DataEvent.UPLOAD_COMPLETE_DATA, completeDataHandler);
			fr.removeEventListener(Event.SELECT,selectHandler);

		}

		private function progressHandler(e:ProgressEvent):void
		{
			var num:uint = (e.bytesLoaded / e.bytesTotal) * 100;
			ExternalInterface.call(upload_id + '_progress',e.bytesLoaded,e.bytesTotal, num);
		}

		private function completeDataHandler(e:Event):void
		{
			trace(e);
			if (isAvailable == true)
			{
				ExternalInterface.call(upload_id + '_completeData',e);
			}

			fr.removeEventListener(DataEvent.UPLOAD_COMPLETE_DATA, completeDataHandler);

		}

		private function completeHandler(e:Event):void
		{
			if (isAvailable == true)
			{
				//trace(e.target.name);
				if (rootFiles == "true")
				{
					var file:FileReference = FileReference(e.target);
					removePendingFile(file);
				}

				ExternalInterface.call('uploadComplete',true);
			}

			fr.removeEventListener(Event.COMPLETE,completeHandler);

		}

	}

}