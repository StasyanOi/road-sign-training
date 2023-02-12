module com {
    opens com.comparators.trigram;
    opens com.comparators;
    opens com.images;

    requires java.base;
    requires org.slf4j;
    requires lombok;
    requires spring.context;
    requires java.desktop;
    requires spring.beans;
}