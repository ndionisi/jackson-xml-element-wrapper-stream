package com.github.ndionisi.xmlelementwrapper;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class JacksonXmlElementWrapperStreamApplicationTest {

    private XmlMapper xmlMapper;

    @Before
    public void setUp() {
        xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new Jdk8Module());
    }

    @Test
    public void should_serialize_list_using_element_wrapper() throws Exception {
        String xml = xmlMapper.writeValueAsString(new ListBasedDto(asList("foo", "bar")));
        assertThat(xml).isEqualTo("<dto><elements><element>foo</element><element>bar</element></elements></dto>");
    }

    @Test
    public void should_serialize_stream_using_element_wrapper() throws Exception {
        String xml = xmlMapper.writeValueAsString(new StreamBasedDto(Stream.of("foo", "bar")));
        assertThat(xml).isEqualTo("<dto><elements><element>foo</element><element>bar</element></elements></dto>");
    }

    @JacksonXmlRootElement(localName = "dto")
    private static class ListBasedDto {
        private List<String> data;

        ListBasedDto(List<String> data) {
            this.data = data;
        }

        @JacksonXmlElementWrapper(localName = "elements")
        @JacksonXmlProperty(localName = "element")
        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }
    }

    @JacksonXmlRootElement(localName = "dto")
    private static class StreamBasedDto {
        private Stream<String> data;

        StreamBasedDto(Stream<String> data) {
            this.data = data;
        }

        @JacksonXmlElementWrapper(localName = "elements")
        @JacksonXmlProperty(localName = "element")
        public Stream<String> getData() {
            return data;
        }

        public void setData(Stream<String> data) {
            this.data = data;
        }
    }

}

