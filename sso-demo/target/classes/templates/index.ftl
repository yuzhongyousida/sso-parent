<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>SSO-DEMO</title>

    <#import "common/common.macro.ftl" as netCommon>
    <@netCommon.commonStyle />
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <@netCommon.commonHeader />
    <@netCommon.commonLeft "help" />

    <div class="content-wrapper">
        <section class="content">
            <h4>home 页面</h4>
        </section>
    </div>

    <!-- footer -->
<@netCommon.commonFooter />
</div>
<@netCommon.commonScript />
</body>
</html>
