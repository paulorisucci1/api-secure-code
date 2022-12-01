package securecodeapi.demo.cryptography;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


@RestController
@RequestMapping(value = "/cryptography", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class CryptoController {

    private CryptoService cryptoService;

    @PostMapping(value = "/aes/encrypt-values")
    public ResponseEntity<JsonNode> encryptAES(@RequestBody JsonNode recievedObject)
            throws NoSuchAlgorithmException {
        return ResponseEntity.ok(cryptoService.encryptAES(recievedObject));
    }

    @PostMapping(value = "/aes/decrypt-values")
    public ResponseEntity<JsonNode> decryptAES(@RequestBody JsonNode recievedObject)
            throws NoSuchAlgorithmException {
        return ResponseEntity.ok(cryptoService.decryptAES(recievedObject));
    }
}
