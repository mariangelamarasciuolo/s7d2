package mariangelamarasciuoloyt.gmail.com.Progettos6d5.payloads;

import java.util.Date;
import java.util.List;

public record ErrorsReponseWithListDTO(String message, Date timeStamp, List<String> errorList) {

}
