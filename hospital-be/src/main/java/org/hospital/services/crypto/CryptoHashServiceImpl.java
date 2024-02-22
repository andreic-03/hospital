package org.hospital.services.crypto;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class CryptoHashServiceImpl implements CryptoHashService {

    @Override
    public String hashContent(String content) {
        // use Keccak-256 as SHA3-256 hashing algorithm
        var digest256 = new Keccak.Digest256();
        byte[] hashBytes = digest256.digest(content.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hashBytes));
    }
}
