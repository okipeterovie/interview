package com.interviewtest.line.remita;



public class RemitaPaymentParams {

    public static class LiveConfig {

        public static String checkStatusUrl = "https://login.remita.net/remita/ecomm";
        public static String gatewayUrl = "https://login.remita.net/remita/exapp/api/v1/send/api/echannelsvc/merchant/api/paymentinit";
        public static String remitaFinalizeUrl = "https://login.remita.net/remita/ecomm/finalize.reg";
        public static String serviceTypeId = "525128309";
        public static String serviceTypeId2 = "525128309";
        public static String merchantId = "520287648";
        public static String apiKey = "532709";
        public static Integer amount = 500;
        public static String remita_00 = "00";
        public static String remita_01 = "01";
        public static String remita_021 = "00";
        public static String remita_045 = "00";

        public static String beneficiaryName = "Interview";
        public static String beneficiaryAccount = "000000";
        public static String beneficiaryBankCode = "000";


        public static String beneficiaryName3Line = "3Line";
        public static String beneficiaryAccount3Line = "000";
        public static String bankCode3Line = "011";
        public static String beneficiaryNameRemita = "3Line company";
        public static String beneficiaryAccountRemita = "4017904612";
        public static String bankCodeRemita = "011";

        public static String beneficiaryNameOasis = "Oasis Management Company";
        public static String beneficiaryAccountOasis = "4017904612";
        public static String bankCodeOasis = "011";
        
    }

    
    public static class DemoConfig {
        
        public static String checkStatusUrl = "https://www.remitademo.net/remita/ecomm"; //demo > 
        public static String gatewayUrl2 = "https://remitademo.net/remita/exapp/api/v1/send/api/echannelsvc/merchant/api/paymentinit"; //demo >
        public static String remitaFinalizeUrl = "https://remitademo.net/remita/ecomm/finalize.reg";
        public static String gatewayUrl = "https://remitademo.net/remita/ecomm/finalize.reg";

        public static String serviceTypeId = "4430731"; //demo > "4430731"
        public static String serviceTypeId2 = "4430731"; //4430731 //demo > "4430731"
        public static String merchantId = "2547916"; //2547916 //demo > 2547916
        public static String apiKey = "1946"; //demo > 1946
        public static Integer amount = 500;
        public static String remita_00 = "00";
        public static String remita_01 = "01";
        public static String remita_021 = "00"; //021
        public static String remita_045 = "00"; //045
        public static String publicKey = "Q0FDfDQwODE2NDc1fDhiYzM2ZGViNDFhMTYxNzVhNjU2NWU0YmNjNDg5NjhhNGUyYTBlYjgzMDBhOGJjZjc2ZDgwMDQwNDNkOTkyN2JjZmIxZTMzNWViODk3ZDI4MTY1Mzk1OWU5YTA1ZDYxZmJlOTRmNzEyNWIzYmJlZWI2Yzg0OGI2M2VjODZmMTQ1";

        public static String beneficiaryName = "Interview";
        public static String beneficiaryAccount = "000";
        public static String beneficiaryBankCode = "011";


        public static String beneficiaryName3Line = "3Line";
        public static String beneficiaryAccount3Line = "000";
        public static String bankCode3Line = "011";
        public static String beneficiaryNameRemita = "3Line company";
        public static String beneficiaryAccountRemita = "000";
        public static String bankCodeRemita = "011";
    }
    
}
