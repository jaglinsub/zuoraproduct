package com.zuora.poc.product.configurations;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    /*@Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        return objectMapper;
    }*/

    /*@Bean
    @Primary
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer());
        javaTimeModule.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

    public static class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {
        @Override
        public void serialize(OffsetDateTime arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
            arg1.writeString(arg0.toString());
        }
    }

    public static class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {
        @Override
        public OffsetDateTime deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException {
            return OffsetDateTime.parse(arg0.getText());
        }
    }

    public static class LocalDateSerializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
            System.out.println("Before arg0: LocalDateSerializer=" + arg0.toString());
            System.out.println("Before arg1: LocalDateSerializer=" + arg1.toString());
            arg1.writeString(arg0.toString());
            System.out.println("After arg0: LocalDateSerializer=" + arg0.toString());
            System.out.println("After arg1: LocalDateSerializer=" + arg1.toString());
        }
    }

    public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException {
            System.out.println("Before arg0: LocalDate Deserializer=" + arg0.getText());
            System.out.println("After arg0: LocalDate Deserializer=" + LocalDate.parse(arg0.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            //return LocalDate.parse(arg0.getText());
            return LocalDate.parse(arg0.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }*/
}
