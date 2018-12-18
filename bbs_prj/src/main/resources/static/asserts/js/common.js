(function($) {

	$.common = {

        /**
         * 校验年龄
         * @param s
         * @returns {boolean}
         */
        isPositiveInteger : function (s) {
            var re = /^[0-9]{1,2}$/;
            return re.test(s);
        },

        /**
         * 校验手机号码
         * @param s
         * @returns {boolean}
         */
        validateMobile : function (s) {
            if (s!="") {
                var re = /^[1][3,5,8][0-9]{9}$/;
                return re.test(s);
            }
        },

        /**
         * 校验身份证
         * @param s
         * @returns {boolean}
         */
        validateCardNo : function (s) {
            var re = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
            return re.test(s);
        },

        /**
         * 密码校验--6-16位字母、数字的组合
         * @param s
         * @returns {boolean}
         */
        validatePwd : function (s) {
            var re = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
            return re.test(s);
        }

	};
})(jQuery);