package src.spec.gpt.convert;

import com.le.emu.mmba.transfer_order.Request;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException, JAXBException {
        byte[] arrayResource = FileCopyUtils.copyToByteArray(new ClassPathResource("order.xml").getInputStream());
        JAXBContext jaxbContext = JAXBContext.newInstance(Request.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // Unmarshal the XML to a MyObject instance
        Request request = (Request) unmarshaller.unmarshal(new ByteArrayInputStream(arrayResource));
        System.out.println(request.getHead().getServOperation());
        System.out.println(request.getData().getAmmount());
        System.out.println();
        System.out.println(request);

    }
}
