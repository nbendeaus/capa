package de.capgemini.capa.service;


import java.io.IOException;
import java.util.UUID;

public class OutlookServiceBuilder {

//    public static OutlookService getOutlookService(String accessToken, String userEmail) {
//        // Create a request interceptor to add headers that belong on
//        // every request
//        Interceptor requestInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Interceptor.Chain chain) throws IOException {
//                Request original = chain.request();
//                Builder builder = original.newBuilder()
//                    .header("User-Agent", "java-tutorial")
//                    .header("client-request-id", UUID.randomUUID().toString())
//                    .header("return-client-request-id", "true")
//                    .header("Authorization", String.format("Bearer %s", accessToken))
//                    .method(original.method(), original.body());
//
//                if (userEmail != null && !userEmail.isEmpty()) {
//                    builder = builder.header("X-AnchorMailbox", userEmail);
//                }
//
//                Request request = builder.build();
//                return chain.proceed(request);
//            }
//        };
//
//        // Create a logging interceptor to log request and responses
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient client = new OkHttpClient.Builder()
//            .addInterceptor(requestInterceptor)
//            .addInterceptor(loggingInterceptor)
//            .build();
//
//        // Create and configure the Retrofit object
//        Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("https://graph.microsoft.com")
//            .client(client)
//            .addConverterFactory(JacksonConverterFactory.create())
//            .build();
//
//        // Generate the token service
//        return retrofit.create(OutlookService.class);
//    }

}
