<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width">
	<meta name="viewport" content="initial-scale=1.0,user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<title>订单支付</title>
	<%@ include file="/WEB-INF/common_andy.jsp" %>
	<script src="${path}/resources/js/jquery-1.8.3.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${path}/resources/azenui/css/ui.css">
    <link rel="stylesheet" href="${path}/resources/mobile/mall/css/style.css" />
    <script src="${path}/resources/mobile/mall/js/jquery.min.js"></script>
    <script src="${path}/resources/mobile/mall/js/jquery.carouFredSel.js"></script>
    <link rel="stylesheet" href="${path}/resources/mobile/mall/topay/css/mui.min.css">
    <link rel="stylesheet" href="${path}/resources/mobile/mall/topay/css/home.css">
    <link rel="stylesheet" type="text/css" href="${path}/resources/mobile/mall/topay/css/icons-extra.css" />
    <link rel="stylesheet" type="text/css" href="${path}/resources/mobile/mall/topay/css/app.css"/>
	<!--=========引入Alert=========-->
	<script type="text/javascript" src="${path}/resources/alert/1.1/alertPopShow.js"></script>
	<script type="text/javascript" src="${path}/resources/alert/1.1/jquery-1.10.1.min.js"></script>
	<link rel="stylesheet" href="${path}/resources/alert/1.1/common.css">

	<script src="${path}/resources/mobile/mall/topay/js/mui.min.js"></script>

	<!-- 微信UI -->
	<link rel="stylesheet" href="${path}/resources/weUI/weui.css">
	<link rel="stylesheet" href="${path}/resources/weUI/example.css">

	<style>
		.xuanfu{
			left: 0;
			position: fixed;
			bottom: 0;
			width: 100%;
			z-index: 100;
		}
		.dialogShare{
			width: 100%; height: auto;
			position: absolute; z-index: 999999999;
			left: 0px; top: 0px;
			background: lightgrey;
			filter:alpha(Opacity=100);-moz-opacity:0.9;opacity: 0.9;progid:DXImageTransform.Microsoft.gradient(startColorstr=#7f333333, endColorstr=#7f333333);
		}
	</style>
  </head>
	<body>
	<div class="dialogShare" style="display:none;">
		<div style="margin-top: 2rem;margin-left: 1.2rem;margin-right: 1.2rem;">
			<div align="center"><p style="color: black;font-size: 1.6rem;">自营旅游产品订购协议</p></div>
			特别提示：本协议为您与四川智胜西行科技有限公司（以下简称“智胜西行”）签订的旅游产品订购协议。在您订购由智胜西行旅行微信公众平台（包括：一个半小时旅游圈和达西）或移动端设备官方网站www.daxi51.com（以下统称为智胜西行官网）页面为您展示的旅游产品前，请您仔细阅读本协议，并注意本协议及产品页面中的其它条款均为本协议内容。智胜西行官网页面发布的各旅游产品具体介绍全部内容（包括但不限于行程详情、预订须知、预订流程、费用说明、退改规则、套餐内容等）均为本协议不可分割的一部分，当您点击智胜西行官网页面任何旅游产品“去支付”按钮，即表明您已经仔细阅读并接受本协议的所有条款，并已将本协议的所有条款告知您即将在订单中登记的旅客（拟实际出游的旅客）。若您及/或您订单中登记的旅客不同意本协议条款和旅游产品介绍页面的部分或全部内容，请勿订购智胜西行官网页面上的任何产品。
			<br/>第一条  旅游产品内容及订购确认
			<br/>1、智胜西行官网上销售的旅游产品包括智胜西行周边游名额产品、智胜西行线路预定名额产品、景点门票、酒店住宿、美食娱乐等产品，上述旅游产品包含智胜西行自营旅游产品和智胜西行受其他经营者/第三方委托销售的旅游产品。
			周边游名额产品是指从出发地到目的地的旅行用车坐位与门票组合，即将您订购该产品时登记的旅客按约定从出发地送达目的地进行观光的产品。周边游名额包括往返车程与景区门票，订购该产品时，请以智胜西行官网页面的产品介绍为准。
			套餐产品指以车位、住宿、门票、船票、景区观光车等两个或两个以上个性化组合成套餐的产品，套餐产品的搭配多变，订购套餐产品时请以智胜西行官网页面介绍的套餐详情为准。您本次订购的本旅游产品为智胜西行自营旅游产品。
			<br/>2、请您认真选择并确认您所订购的旅游产品，您所订购的旅游产品以您在智胜西行官网网页点击“去支付”确认的产品为准。当您点击旅游产品购买页面“去支付”按钮前，请您认真仔细阅读您所拟订购的旅游产品具体介绍全部内容（包括但不限于行程详情、预订须知、预订流程、费用说明、退改规则、预订流程、旅客评价、套餐内容等），并就相关疑问向在线客户进行沟通、了解。当您点击“去支付”前，请您按页面提示认真准确填写旅客信息、联系人信息、预订人信息等页面要求填写的内容，并对所填信息的真实性、准确性负责。若您对拟订购产品有疑问，请务必向工作人员咨询，了解并确认后再进行订购。当您点击“去支付”按钮，即表明您已仔细阅读并已确认同意您所订购旅游产品页面具体介绍的全部内容及本协议内容，并自愿遵守。
			<br/>3、在您购买本旅游产品时，智胜西行无法审查您购买产品时登记的旅客身份信息与实际参与旅程的旅客是否为同一人，您及/或订单中登记的旅客应在使用本旅游产品时向我们出示本次旅游产品的购买凭证（如扫码乘车的二维码或其他有效兑换凭证）。您及/或您订单中登记的旅客无权以智胜西行未核对身份信息为由向智胜西行主张违约责任或损害赔偿责任。请妥善保护好您的账号及兑换码，否则由此造成的损失由您自行承担。
			<br/>第二条 订购者及出行旅客要求
			<br/>1、若您是未满十八周岁的未成年人或其他不具备完全民事行为能力人，请勿购买智胜西行官网任何旅游产品。
			<br/>2、若您为未成年人或其他不具备完全民事行为能力的人购买智胜西行官网旅游产品，务必有60周岁以下具有完全民事行为能力的成年亲属作为监护人一起参加本次旅游，并对未成年人和不具备完全民事行为能力者的人身安全和财产安全负责。
			<br/>3、60周岁以上老年旅客，智胜西行根据需要向其了解个人健康状况、个人通讯方式、紧急联络人信息等信息时，旅客应予配合并签字确认。不配合提供或不签字确认的，智胜西行有权拒绝其参加行程而不退还已发生的交通费、门票等费用。
			<br/>4、70周岁以上老年旅客必须有60周岁以下具有完全民事行为能力的成年健康亲属陪同出游，并由该亲属和老年旅客共同与智胜西行签订《老年人游客健康协议》，否则智胜西行有权拒绝其参加行程而不退还已发生的交通费、门票等费用。
			<br/>5、根据旅游目的地的不同，智胜西行有权根据旅客的身体健康状况及相关条件决定是否接纳旅客参加，不接纳的情形在产品介绍中具体列明，请订购者勿对该等旅客购买产品，您订购后退订的，按退订规则执行；您不退订的，智胜西行有权拒绝该等旅客出游而无需退还任何费用。
			<br/>6、若未成年人或其他不具备完全民事行为能力的人单独购买或单独作为旅客参加购买的旅游产品项目或盗用、借用他人身份证明证件参与本次旅游项目，视为违约，智胜西行一经发现有权拒绝提供服务而无需退还您任何费用。智胜西行无法审查产品使用人是否为未成年人或无民事行为能力人，对该等旅客在出行期间发生的人身伤亡或财产损失概不承担任何责任。
			<br/>7、因交通运输监管部门对旅游交通车的特殊性要求，旅游交通车必须一人一座，若出行人员拟携带未成年人（包括婴幼儿）出游，请务必为其订购车位产品。否则我们有权拒绝其上车，由此造成的损失由订购者自行承担。携带婴幼儿未购票的，智胜西行无法为其购买保险，因此造成的损失，由您自行承担。
			<br/>第三条  旅游产品价格
			<br/>智胜西行官网页面展示的产品价格仅为参考价格，由于旅游产品价格的波动性，智胜西行官网可能因为车辆、酒店、门票等的价格变动而适度调整在智胜西行官网上已公布的旅游产品的价格。您预订的所有旅游产品的价格，以最后支付的金额为准。
			在您点击“去支付”前，可根据提示选择您可用优惠券进行产品价格抵扣。请您注意优惠券的使用时间，过期的优惠券将不能进行使用。
			<br/>第四条  订单生效
			<br/>1、您在智胜西行官网订购产品并按订单要求付清应付款项时，您确认的订单即生效。但如您未按要求在预订付款期前付清相关费用，而此时智胜西行官网为您预留的产品的价格、内容或标准等有发生变化，您需要按变更后的价格、内容或标准执行，否则智胜西行有权单方面取消您的订单并对此不构成任何违约责任。 订单生效，即代表您与智胜西行的合作意向已经达成，您的变更、解除旅游产品等的需求，将按旅游产品介绍页面具体介绍内容和本协议相关条款的约定执行。
			<br/>2、本协议生效后，您及/或您订单中登记的旅客应在发车时间前到达指定集合地点，订购的产品包含住宿项目的，需按订单中确定的日期在确定的时间入住酒店。如您及/或您订单中登记的旅客未准时到达指定集合地点，智胜西行有权直接发车不予等待。因您及/或您订单中登记的旅客未准时达到集合地点或未能按时入住所订酒店或未按旅游产品介绍的内容履行相应义务，您订购该旅游产品已支付的全部费用将不予退还且智胜西行无需承担任何违约责任，您及/或您订单中登记的旅客由此而支出的额外费用亦由您及/或您订单中登记的旅客自行承担。
			<br/>第五条   本合同的变更与订单转让
			<br/>1、订单生效后，若您需要更改该订单内的任何项目，请务必按旅游产品介绍页面的要求和途径提出更改申请。我们会根据旅游产品的剩余情况协调变更，您必须全额承担因变更带来的全部损失及可能增加的费用。因产品库存原因无法变更的，您可选择退订或者不予变更，退订的，按退改规则执行。
			<br/>若您所预订的产品在目的地停留的日期部分或全部处在国家法定节假日或其它部分国际性、国家性、地方性重大节日期间，鉴于旅游资源的特殊状况，已生效订单智胜西行有权拒绝任何更改。
			<br/>2、行程开始前遇到不可抗力或者智胜西行已尽合理注意义务仍不能避免的事件的，双方经协商可以取消行程或者延期出行。延期出行的，增加的费用由您承担，减少的费用退还给您；取消行程的，智胜西行向您退还已支付的全部费用。
			<br/>3、行程中遇到不可抗力或者智胜西行已尽合理注意义务仍不能避免的事件，影响旅游行程的，按以下方式处理：
			<br/>（1）合同不能完全履行的，智胜西行经向旅客作出说明，旅客同意变更的，可以在合理范围内变更合同，因此增加的费用由旅客承担，减少的费用退还旅客。
			<br/>（2）造成旅客滞留的，智胜西行积极采取相应的安置措施。因此增加的食宿费用由旅客承担，增加的返程费用双方分担。
			<br/>4、订单一经生效，不能进行转让。实际出游的旅客必须与订单中登记的旅客一致。因您擅自转让或更换旅客而造成的一切损失由您自行承担，智胜西行不承担任何责任。
			<br/>第六条   订单解除
			<br/>1、订购者解除合同
			<br/>订单生效后，若您要主动解除已生效订单，请您务必按旅游产品介绍页面介绍的退改规则和途径就您需要解除的订单提出申请。通常情况下我们会根据以下退改规则执行，如具体旅游产品介绍页面介绍的退改规则与以下规则不一致的，则以具体旅游产品介绍页面介绍的退改规则为准：
			<br/>（1）在行程开始前7日以上提出解除合同的，向您全额退还订单金额；
			<br/>（2）在行程开始前6日至4日提出解除合同的，扣除订单金额的20%必要费用后向您退还余下费用；
			<br/>（3）在行程开始前3日至1日提出解除合同的，扣除订单金额的40%必要费用后向您退还余下费用；
			<br/>（4）行程开始当日零时起，不能解除合同，所收取的订单金额不予退还。
			<br/>2、智胜西行解除合同
			<br/>旅客有下列情形之一的，智胜西行可解除本合同，且无需退还任何已支付费用：
			<br/>（1）患有传染病等疾病，可能危害其他旅客健康和安全的；
			<br/>（2）携带危害公共安全的物品且不同意交有关部门处理的；
			<br/>（3）从事违法或者违反社会公德的活动的；
			<br/>（4）从事严重影响其他旅客权益的活动，且不听劝阻、不能制止的；
			<br/>（5）旅客为未成年人无60周岁以下身体健康成年家属陪同的；
			<br/>（6）旅客为无民事行为能力者无60周岁以下身体健康成年家属陪同的。
			<br/>（7）70周岁以上老年旅客无60周岁以下身体健康成年家属陪同的。
			<br/>（8）法律、法规规定的其他情形。
			<br/>3、行程开始前7日（含7日），智胜西行可通过您预留的联系电话短信通知您解除本合同而无需承担违约责任，智胜西行向您全额退还已支付的费用。
			<br/>行程开始前7日以内（不含7日）提出解除合同的，除向您退还已收取的全部费用外，按如下标准向您支付违约金：
			<br/>行程开始前6日至4日，支付订单金额10%的违约金；
			<br/>行程开始前3日至1日，支付订单金额15%的违约金；
			<br/>行程开始当日，支付订单金额20%的违约金。
			<br/>4、您订购的本产品的最低成团人数应符合本旅游产品介绍页面所要求的成团人数介绍，若人数不足，智胜西行有权取消行程，并提前一天电话或短信通知您。因不能满足最低成团人数而取消行程的，智胜西行向您全额退还订单金额而无需支付任何违约金。
			<br/>第七条   风险告知与警示
			<br/>（一）游客如您及/或您订单中登记的旅客存在包括但不限于下列不事宜参加旅游活动的情形，请勿购买本产品出游，否则一切责任自行承担：
			<br/>（1）传染性疾病患者，如传染性肝炎、活动期肺结核、伤寒等传染病人；
			<br/>（2）心血管疾病患者，如高血压、心功能不全、心肌缺氧、心肌梗塞等病人；
			<br/>（3）脑血管疾病患者，如老栓塞、脑出血、脑肿瘤等病人；
			<br/>（4）呼吸系统疾病患者，如肺气肿、肺心病等病人；
			<br/>（5）精神病患者，如癫痫及各种精神病人；
			<br/>（6）严重贫血患者，如血红蛋白量水平在50克/升以下病人；
			<br/>（7）大中型手术的恢复期病患者；
			<br/>（8）孕妇及行动不便者。
			<br/>请您及/或您订单中登记的旅客在出行前做一次必要的身体检查，并征得专业医生的建议后再出游。
			<br/>（二）高原反应风险告知及警示
			<br/>请您及/或您订单中登记的旅客务必注意：旅游目的地为川藏线如九寨沟、毕棚沟、稻城亚丁、海螺沟等区域海拔高。海拔较高地带，气压低，空气含氧量少，易导致人体缺氧，引起高原不良反应。针对不同身体条件人群会有不同高原反应。请您在订购前务必充分考量您及/或您即将在订单中登记的旅客（拟实际出游的旅客）的身体健康状况及年龄是否适宜出游，建议在预订产品前自行前往医疗机构体检并咨询医院专业医生意见，以确保您及/或您即将在订单中登记的旅客（拟实际出游的旅客）身体条件能够完成您所订购的旅游活动。
			<br/>1、患有以下疾病的人员不宜到高原地区旅游：
			<br/>（1) 高血压患者；
			<br/>（2) 患有器质性心脏病、脑血管疾病患者；
			<br/>（3) 糖尿病患者；
			<br/>（4) 患有慢性肺病的患者，各种呼吸功能不全；支气管哮喘病人；
			<br/>（5) 各种血液病患者；
			<br/>（6) 各种心肺疾病引起的肺动脉高压患者；
			<br/>（7) 睡眠中容易出现呼吸暂停的人；
			<br/>（8) 癔病、癫痫、精神分裂症等神经与精神性疾病患者；
			<br/>（9) 重症感冒、呼吸道感染的人；
			<br/>（10) 以往患过高原病及其他严重慢性疾病的人；
			<br/>（11)未成年人不适宜进入高原地区。因为未成年人正处于身体发育的时期，对高原低氧环境十分敏感，容易缺氧而发生急性高原病；
			<br/>（12)老年人不适宜进入高原地区，由于老年人身体机能下降，免疫力和应对特殊环境的能力不足，容易感冒，更易发生急性高原病，且不易救治；。
			<br/>（13）怀孕者不适宜进入高原地区。
			<br/>2、前往高原地区的注意事项
			<br/>（1）从决定去高原旅游起，在日常生活中适当增加些无氧锻炼，这样可使肌体对缺氧状态产 生一定的耐受力；
			<br/>（2）高原地区早晚温差可达 15—20 摄氏度，需要带上足够的防寒衣物；
			<br/>（3）准备好抗紫外线的防护用品；
			<br/>（4）避免和消除发病诱因。寒冷、过劳、呼吸道感染常是发病诱因。初进高原注意防止过劳， 防寒保暖，防止呼吸道感染是很重要的；
			<br/>（5）在进高原过程中及初到高原，少做体力活动及其他剧烈活动；
			<br/>（6）宜用清淡、富含维生素、易消化饮食。多喝水、多吃水果，不宜过饱，忌饮酒。
			<br/>我们强烈建议对具有包括但不限于第七条所列各项不宜出游情形者不要选择本旅游产品，请您订购本旅游产品时充分慎重考虑。如果您及/或您即将在订单中登记的旅客（拟实际出游的旅客）经充分慎重考虑后仍要出游，应有60周岁以下具有完全民事行为能力且身体健康的成年家属陪同、照顾。
			<br/>（三）高风险项目风险告知及警示
			<br/>1、高风险项目包括但不限于：滑雪、漂流、登山、水上运动、空中项目、温泉、前往海拔高的地方（高原反应）、攀岩、潜水等。
			<br/>2、若您订购的产品涉及高风险项目或您自由行过程中将要参与高风险项目，请您务必在订购前请专业医生对您及/或您即将在订单中登记的旅客（拟实际出游的旅客）是否适宜参与高风险项目进行评估，参与高风险项目时，请务必在专业老师的指导下完成并做好安全防范工作。为了您及/或您即将在订单中登记的旅客（拟实际出游的旅客）的安全考虑，不具备参与高风险项目条件的，请务必不要参与。
			<br/>（四）免责条款
			<br/>1、在行程中因您及/或您订单中登记的旅客自身原因（包括但不限于身体条件、健康原因、过错、过失等）引发的疾病或造成的人身伤亡、财产损失的，由您及/或您订单中登记的旅客本人承担相关责任，智胜西行、智胜西行微信公众平台运营商概不承担任何责任。
			<br/>2、本旅游项目为自由行项目，您及/或您订单中登记的旅客在自行安排活动期间，应当在自己能够控制风险的范围内选择活动项目，遵守旅游活动中的安全警示规定，妥善保管财物，对自己的安全和财物负责。您及/或您订单中登记的旅客在自由安排活动期间人身、财产权益受到损害的，智胜西行不承担任何责任。
			<br/>除在出行车辆上的时间均为自由安排活动期间，包括但不限于行程开始前、中途停靠休息期间、到达目的地乘客下车后时间等。
			<br/>第八条 您及/或您订单中登记的旅客的权利和义务
			<br/>1、您及/或您订单中登记的旅客应遵守我国和旅游目的地国家（地区）的法律、法规和有关规定，不携带违禁物品出入境；不参与色情、赌博和涉毒活动；不在境外滞留不归。
			<br/>2、您及/或您订单中登记的旅客在出行期间，应遵守旅游目的地国家（地区）的公共秩序和社会公德，尊重当地的风俗习惯，文化传统和宗教信仰，爱护旅游资源，保护生态环境，遵守《中国公民出国（境）旅游文明行为指南》等文明行为规范。
			<br/>3、您及/或您订单中登记的旅客出行期间，对国家应对重大突发事件暂时限制旅游活动的措施，以及有关部门、机构或者旅游经营者采取的安全防范和应急处置措施予以配合。
			<br/>4、您及/或您订单中登记的旅客在旅游活动中或者在解决纠纷时，应采取适当措施防止损失扩大，不损害当地居民的合法权益，不干扰他人的旅游活动，不损害旅游经营者和旅游从业人员的合法权益，不采取拒绝上、下机（车、船）等不当行为。
			<br/>5、您及/或您订单中登记的旅客保证提供给智胜西行的证件、通讯联络方式、地址等相关资料均真实、有效且不得变更，否则由您及/或您订单中登记的旅客承担全部损失。您及/或您订单中登记的旅客预留的联系电话、地址为送达的联系方式和地址。
			<br/>6、您及/或您订单中登记的旅客在旅游过程中如对智胜西行的服务质量有异议，应积极与智胜西行沟通，积极争取在旅游过程中解决。您及/或您订单中登记的旅客可以选择通过智胜西行官网客服或智胜西行投诉电话进行投诉。
			<br/>7、如您不遵守《智胜西行官网会员服务协议》，或您及/或您订单中登记的旅客不遵守本协议的约定，恶意干扰智胜西行官网的正常运营，恶意预订、更改或退订旅游产品，或者恶意向第三方机构投诉智胜西行微信公众平台或智胜西行，智胜西行保留追究您及/或您订单中登记的旅客个人责任的权利。
			<br/>8、旅途过程中，若同行旅客发生身体不适或其他影响人身安全的事件，行车司机和行车助理会在第一时间将其送往附近的医院救治，因此所耽误您及/或您订单中登记的旅客行程的，不视为违约，您及/或您订单中登记的旅客需表示谅解。
			<br/>9、由于您及/或您订单中登记的旅客的过错，使智胜西行、旅游产品供应商、司机、行车助理、或其他旅游者等遭受损害的，您及/或您订单中登记的旅客应当赔偿损失。
			<br/>10、您及/或您订单中登记的旅客应按照具体旅行产品确定的发车时间前到达集合地点上车，为避免耽误其他旅客时间，我们将在确定的发车时间进行发车，如您及/或您订单中登记的旅客晚点未上车，智胜西行不退还您任何费用，损失由您及/或您订单中登记的旅客自行承担。
			<br/>11、您及/或您订单中登记的旅客应按照规定投保人身意外伤害保险，旅游产品包含人身意外伤害保险的，智胜西行将根据您订购旅游产品时登记的旅客身份证号码信息代旅客购买保险。因您未填写身份证号码、填写错误、冒用或盗用他人身份证号码等非智胜西行原因导致智胜西行未能为您及/或您订单中登记的旅客购买保险或购买保险的被保险人与实际出行旅客不符合的，由此造成的损失由您及/或您订单中登记的旅客自行承担。
			<br/>旅游产品不包含人身意外伤害保险的，在此特别提醒，请您及实际出游者自行购买。因未购买造成的一切损失自行承担。
			<br/>第九条 智胜西行的权利和义务
			<br/>1、您确认您及/或您订单中登记的旅客是自由行旅游，旅游过程中，您及/或您订单中登记的旅客应充分注意人身财产安全，妥善保管自己的证件及行李物品，如果发生人身伤亡或随身携带行李物品遗失、被盗、被抢等财产损失情况，智胜西行、智胜西行“官网”运营商会积极协助处理，但无任何赔偿之责任。您及/或您订单中登记的旅客在自由行过程中发生的人身及财产损失由您们自己承担，请务必注意安全。
			<br/>3、智胜西行保证行程过程中不会出现强迫或变相强迫购物、参加另行付费旅游项目的行为。
			<br/>4、针对车位产品，我们保证有专业的行车助理在行程途中为您提供服务、进行讲解。
			<br/>第十条  涉及第三方的相关责任
			<br/>1、第三方指在本次旅游项目中涉及的保险公司、目的地景区、酒店、饭店、同行团友、驾驶员及其他相关机构、企业和个人。智胜西行无法控制您及/或您订单中登记的旅客与第三方出现的任何情况，智胜西行承诺积极协助处理，但相应结果应由您及/或您订单中登记的旅客自行承担。
			<br/>2、您及/或您订单中登记的旅客在旅行中违反相关国家或地区的法律、法规而被处罚、被拘留、遣返及被追究其他刑事、民事责任的，您及/或您订单中登记的旅客应依法承担相关责任和费用。
			<br/>3、您及/或您订单中登记的旅客违反我国及前往国家（地区）的有关规定，携带宠物、危险物品或违禁品而产生的一切后果自行承担。
			<br/>第十一条  不可抗力
			<br/>1、因不可抗力（包括但不限于地震、台风、雷击、雪灾、水灾、火灾等自然原因，以及堵车、战争、政府行为、黑客攻击、电信部门技术管制等原因）和意外事件等非因智胜西行原因导致不能履行或不能继续履行已生效订单约定内容的，双方均不承担违约责任。
			<br/>2、如果由于第三方不可控因素造成的价格上涨，对于已成交的订单，不再向您及/或您订单中登记的旅客收取涨价费用；对于已确认但未付款和未确认的订单，则以新价格为准。
			<br/>第十二条 争议解决
			<br/>因履行本协议发生的争议，由双方协商解决，协商不成的，由四川智胜西行科技有限公司住所地人民法院管辖。
			<br/>第十三条 协议生效
			<br/>本协议于您的订单支付成功之时生效，对智胜西行、您及/或您订单中登记的旅客均具有约束力。
			<!--<div align="center" onclick="heidTZ()" style="margin-top: 1.5rem;"><button style="color: #000000;background-color: #F8F8F8;width: 50%;height: 2rem;color: grey;">关 闭</button></div> -->
			<div class="xuanfu" align="center">
				<button style="color: #000000;background-color: #F8F8F8;width: 100%;height: 49px;color: grey;" onclick="heidTZ()">关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
			</div>
			<div style="margin-top: 5rem;">&nbsp;</div>
		</div>
	</div>
	<div style="height:1rem"></div>
	<div class="mui-content">
		<div class="mui-card">
			<form class="mui-input-group">
                <div class="mui-input-row mui-radio b-line">
                    <label>订单号：<span>${order.orderNo}</span></label>
                </div>
                <div class="mui-input-row mui-radio b-line" style="height: auto;">
                    <label>
                        <div>${order.orderName}<br/></div>
                        <div style="margin-top: 0.5rem;">${order.numberDescM}<br/></div>
                        <div style="margin-top: 0.5rem;">${order.userUseTime}</div>
					</label>
                </div>
				<div class="mui-input-row mui-radio b-line">
					<label><span class="mui-icon mui-icon-weixin" style="color:#57dc0f"></span>微信支付</label>
					<input name="payType" type="radio" checked="checked" value="WECHAT_PAY" style="margin-top: 0.45rem;">
				</div>
				<div class="mui-input-row mui-radio b-line" style="display: none;">
					<label><span class="mui-icon mui-icon-weixin" style="color:#57dc0f"></span>支付宝支付</label>
					<input  name="payType" type="radio" value="ALIBABA_PAY" style="margin-top: 0.45rem;">
				</div>
			</form>
		</div>
	</div>

	<div class="xuanfu" style="margin-bottom: 52px;margin-left: 1.2rem;">
		<img src="${path}/resources/img/icon/topay/c1.png" width="20px;" id="hideimg1"/>
		<img src="${path}/resources/img/icon/topay/c2.png" width="20px;" id="hideimg2" style="display: none" />
		同意<a style="color: #0a49fc" onclick="showTZ()">预定须知和重要条款</a>
	</div>
	<div class="mui-bar mui-bar-tab">
		<div class="t-line aui-on-cell">
			<div class="aui-onc">
				<span style="color:#f56e4e;margin-right: 6rem;">合计 ：¥${order.orderAmountMoney}</span>
				<a href="JavaScript:;" class="aui-got" style="padding:0 54px;" onclick="callPay(${order.orderAmountMoney})">付&nbsp;&nbsp;款</a>
				<%--<a href="JavaScript:;" class="aui-got" style="padding:0 54px;" onclick="callPayTest(${order.orderAmountMoney})">测试付款</a>--%>
</div>
</div>
</div>

<input type="hidden" id="order_no" value="${order.orderNo}"/><br>
<input type="hidden" id="orderType" value="${order.orderType}"/><br>
<input type="hidden" id="order_amountMoney" value="${order.orderAmountMoney}"/><br>
<input type="hidden" id="order_name" value="${order.orderName}"/><br>
<input type="hidden" id="openid" value="${user.openid}"/><br>
<input type="hidden" id="userId" value="${user.id}"/><br>


<!-- loading toast -->
<div id="loadingToast" style="display:none;">
<div class="weui-mask_transparent"></div>
<div class="weui-toast">
<i class="weui-loading weui-icon_toast"></i>
<p class="weui-toast__content">付款中</p>
</div>
</div>

</body>
<script type="text/javascript">
checked = 0;
function showTZ() {
$(".dialogShare").show();
}
function heidTZ() {
$(".dialogShare").hide();
}
$("#hideimg1").click(function(){
checked = 1;
$("#hideimg1").hide();
$("#hideimg2").show();
});

$("#hideimg2").click(function(){
checked = 0;
$("#hideimg2").hide();
$("#hideimg1").show();
});

//定义支付所需变量
appId = "";
timeStamp = "";
nonceStr = "";
package = "";
signType = "";
paySign = "";
prepayId = "";
orderNo = '${order.orderNo}';//订单编号
body = '${order.orderName}';//商品描述
openId = '${user.openid}';//用户openId
userId = '${user.id}';//用户id
money = '${order.orderAmountMoney}';//支付金额，这里需要按照单价计算总价
menuId = '${menuId}';//公众号id，系统菜单id
/**
* 支付调取函数
* param money 支付金额
*/
function callPay(moneytemp){
if(checked == 0){
    webToast("请勾选预定须知！","middle",2000);//top middle bottom 控制显示位置
}else{
    /** 加载提示 */
    var $loadingToast = $('#loadingToast');
    $loadingToast.fadeIn(100);
    setTimeout(function () {
        $loadingToast.fadeOut(100);
    }, 1200);

    var cid = "${cid}";
    //判断订单是否已经满了
    $.post("${path}/commodity/interfaceGetTotalOrder",{id:cid},function(result){
        if(result == "ok"){

            money = moneytemp;
            /** 判断订单是否支付*/
            $.get("${path}/order/getOrderTypeStatusNoUser",{orderNo:orderNo},function(info){
                if(info == "0"){
                    $.get("${path}/order/getOrderTypeStatusNoUser",{orderNo:orderNo},function(info){
                        if(info == "0"){
                            payType();
                        }else if(info == "-1") {
                            webToast("获取订单状态错误，请刷新后重试！","middle",2000);//top middle bottom 控制显示位置
                        }
                        else if(info == "1") {
                            payType();
                            //webToast("该订单已经支付了！","middle",2000);//top middle bottom 控制显示位置
                        }
                    });
                }else if(info == "-1") {
                    webToast("获取订单状态错误，请刷新后重试！","middle",2000);//top middle bottom 控制显示位置
                }
                else if(info == "1") {
                    payType();
                    //webToast("该订单已经支付了！","middle",2000);//top middle bottom 控制显示位置
                }
            });
        }
        else{
            webToast("报名已满，请下次参与！","middle",2000);//top middle bottom 控制显示位置
            return false;
        }
    });

}
}


//测试版支付
function callPayTest(moneytemp) {
if(checked == 0){
    webToast("请勾选预定须知！","middle",2000);//top middle bottom 控制显示位置
}else{
    /** 加载提示 */
    var $loadingToast = $('#loadingToast');
    $loadingToast.fadeIn(100);
    setTimeout(function () {
        $loadingToast.fadeOut(100);
    }, 1200);

    var cid = "${cid}";
    //判断订单是否已经满了
    $.post("${path}/commodity/interfaceGetTotalOrder",{id:cid},function(result){
        if(result == "ok"){
            money = moneytemp;
            /** 判断订单是否支付*/
            $.get("${path}/order/getOrderTypeStatusNoUser",{orderNo:orderNo},function(info){
                if(info == "0"){
                    $.get("${path}/order/getOrderTypeStatusNoUser",{orderNo:orderNo},function(info){
                        if(info == "0"){
                            payComplete();
                        }else if(info == "-1") {
                            webToast("获取订单状态错误，请刷新后重试！","middle",2000);//top middle bottom 控制显示位置
                        }
                        else if(info == "1") {
                            webToast("该订单已经支付了！","middle",2000);//top middle bottom 控制显示位置
                        }
                    });
                }else if(info == "-1") {
                    webToast("获取订单状态错误，请刷新后重试！","middle",2000);//top middle bottom 控制显示位置
                }
                else if(info == "1") {
                    webToast("该订单已经支付了！","middle",2000);//top middle bottom 控制显示位置
                }
            });
        }
        else{
            webToast("报名已满，请下次参与！","middle",2000);//top middle bottom 控制显示位置
            return false;
        }
    });

}
}



function payType() {
var payType = $("input[name='payType']:checked").val();
if(payType == "WECHAT_PAY"){
    getPayParam();
}else if(payType == "ALIBABA_PAY") {
    webToast("暂不支持支付宝支付！","middle",2000);//top middle bottom 控制显示位置
}
}

/**
* 支付参赛获取
*/
function getPayParam(){
prepayId = "";
$.post("${path}/pay/topayServletNoUser",{menuId:menuId,openId:openId,userId:userId,orderNo:orderNo,money:money,body:body},function(info){
     var obj = eval("("+info+")");
     for(var i = 0;i<1;i++ ){
         appId = obj[i].appId;
         timeStamp = obj[i].timeStamp;
         nonceStr = obj[i].nonceStr;
         packageValue = obj[i].packageValue;
         signType = obj[i].signType;
         paySign = obj[i].paySign;
        prepayId = obj[i].prepayId;
     }
     if(prepayId =="" || prepayId == null){
        webToast("预支付获取失败!","middle",2000);//top middle bottom 控制显示位置
    }else{
        pay(appId,timeStamp,nonceStr,packageValue,signType,paySign);//调用支付
    }
 }).error(function(){
    webToast("拉取支付失败!","middle",2000);//top middle bottom 控制显示位置
});
}

/**
* 正式调用支付
* param appId 公众号appid
* param nonceStr 时间戳，自1970年以来的秒数
* param packageValue 随机串
* param signType 签名方式 MD5
* param paySign 微信签名
*/
function pay(appId,timeStamp,nonceStr,packageValue,signType,paySign){
WeixinJSBridge.invoke('getBrandWCPayRequest',{"appId" : appId,"timeStamp" : timeStamp, "nonceStr" : nonceStr, "package" : packageValue,"signType" : signType, "paySign" : paySign},function(res){
    WeixinJSBridge.log(res.err_msg);
    //alert(res.err_code + res.err_desc + res.err_msg);
    if(res.err_msg == "get_brand_wcpay_request:ok"){
        //alert("微信支付成功!");
        payComplete();
    }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
        //alert("用户取消支付!");
        webToast("支付失败，你取消支付!","middle",2000);//top middle bottom 控制显示位置
    }else{
        //alert("支付失败!");
        webToast("支付失败，请重试!","middle",2000);//top middle bottom 控制显示位置
    }
    })

}

/**
* 支付调取函数
* param money 支付金额
*/
function payComplete(){
var order_no = $("#order_no").val();
var orderType = $("#orderType").val();
var menuId = '${menuId}';
$.post("${path}/order/payOrderCompleteInUser",{order_no:order_no},function(info){
  //(1) 1订单付款成功    (2) -1订单已经付款成功，不要重复付款 (3)0订单不存在
  if(info == 1){
      webToast("支付成功！","middle",2000);//top middle bottom 控制显示位置
    if(orderType == 1){
        //window.location.href = "${path}/wechatInUser/userOrder?menuId=" + menuId ;
        window.location.href = "${path}/wechatInUser/userOrder?menuId=" + menuId + "&currentPage=1&typeState=-1";
    }
    else if(orderType == 2){
        window.location.href = "${path}/myGroupInUser/list?menuId=" + menuId + "&cpage=1";
    }
  }else if(info == 0){
      webToast("支付失败，该订单已经过期！","middle",2000);//top middle bottom 控制显示位置
  }else if(info == -1){
      webToast("支付失败，该订单已经支付完成了！","middle",2000);//top middle bottom 控制显示位置
    if(orderType == 1){
        window.location.href = "${path}/wechatInUser/userOrder?menuId=" + menuId + "&currentPage=1&typeState=-1";
    }
    else if(orderType == 2){
        window.location.href = "${path}/myGroupInUser/list?menuId=" + menuId + "&cpage=1";
    }
  }
});
}
</script>
</html>