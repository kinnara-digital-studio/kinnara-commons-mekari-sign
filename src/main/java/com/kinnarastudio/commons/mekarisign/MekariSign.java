package com.kinnarastudio.commons.mekarisign;

import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.*;
import com.kinnarastudio.commons.mekarisign.service.*;

import java.io.*;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Base64;

public class MekariSign {
    public final static int BYTE_ARRAY_BUFFER_SIZE = 4096;

    private final ServerType serverType;
    private final AuthenticationToken authenticationToken;

    public MekariSign(ServerType serverType, AuthenticationToken authenticationToken) {
        this.serverType = serverType;
        this.authenticationToken = authenticationToken;
    }

    public void globalSign(File file, RequestSigner signer) throws RequestException {
        globalSign(file, new RequestSigner[]{signer});
    }

    // public void globalSign(InputStream inputStream, String filename, RequestSigner signer) throws RequestException {
    //     globalSign(inputStream, filename, new RequestSigner[]{signer});
    // }

    public void globalSign(File file, RequestSigner[] signers) throws RequestException {
        try (final InputStream is = Files.newInputStream(file.toPath())) {
            final String filename = file.getName();
            globalSign(is, filename, signers);
        } catch (IOException e) {
            throw new RequestException(e.getMessage(), e);
        }
    }

    public void globalSign(InputStream inputStream, String filename, RequestSigner[] signers) throws RequestException {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream(BYTE_ARRAY_BUFFER_SIZE)) {
            final Base64.Encoder encoder = Base64.getEncoder();
            final byte[] buffer = new byte[BYTE_ARRAY_BUFFER_SIZE];
            while (inputStream.read(buffer) > 0) {
                bos.write(buffer);
            }

            final String doc = encoder.encodeToString(bos.toByteArray());
            final GlobalSigner globalSigner = GlobalSigner.getInstance();
            globalSigner.requestSign(serverType, authenticationToken, new SignRequest(filename, doc, signers));
        } catch (IOException e) {
            throw new RequestException(e.getMessage(), e);
        }
    }

    public void psreSign(File file, RequestSigner reqSigner) throws RequestException, ParseException{
        psreSign(file, new RequestSigner[]{reqSigner});
    }

    private void autoSign(InputStream inputStream, String[] docMakerEmails, String[] signerEmails) {
        autoSign(inputStream, new String[]{Arrays.toString(docMakerEmails)}, new String[]{Arrays.toString(signerEmails)});
    }

    public void psreSign(InputStream inputStream, String filename, RequestSigner signer) throws RequestException, ParseException {
        psreSign(inputStream, filename, new RequestSigner[]{signer});
    }

    public void psreSign(File file, RequestSigner[] signers) throws RequestException, ParseException {
        try (final InputStream is = Files.newInputStream(file.toPath())) {
            final String filename = file.getName();
            psreSign(is, filename, signers);
        } catch (IOException e) {
            throw new RequestException(e.getMessage(), e);
        }
    }

    public void getDoc(int page, int limit,DocumentCategory category ,SigningStatus status, StampingStatus stamping) throws RequestException, ParseException
    {
        DocumentListGetter docListGet = DocumentListGetter.getInstance();
        docListGet.requestDocs(serverType, authenticationToken, page, limit,category, status, stamping);
    }

    public void getProfile() throws RequestException, ParseException
    {
        UserProfile userProfile = UserProfile.getInstance();
        userProfile.requestProfile(serverType, authenticationToken);
    }

    public void getDocDetail(String id) throws RequestException, ParseException
    {
        DocumentDetail docListDetail = DocumentDetail.getInstance();
        docListDetail.requestDocs(serverType, authenticationToken, id);
    }
    
    public void psreSign(InputStream inputStream, String filename, RequestSigner[] signers) throws RequestException, ParseException {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream(BYTE_ARRAY_BUFFER_SIZE)) {
            final Base64.Encoder encoder = Base64.getEncoder();
            final byte[] buffer = new byte[BYTE_ARRAY_BUFFER_SIZE];
            while (inputStream.read(buffer) > 0) {
                bos.write(buffer);
            }

            final String doc = encoder.encodeToString(bos.toByteArray());
            final PSrESigner psre = PSrESigner.getInstance();
            psre.requestSign(serverType, authenticationToken, new SignRequest(filename, doc, signers));
        } catch (IOException e) {
            throw new RequestException(e.getMessage(), e);
        }
    }

    public void autoSign(ReqAutoSign reqAutoSign) throws RequestException{
        final AutoSign autoSign = AutoSign.getInstance();
        try {
            autoSign.requestAutoSign(serverType, authenticationToken, reqAutoSign);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAutoSign(String autoSignId) throws RequestException {
        final DeleteAutoSign delete = DeleteAutoSign.getInstance();
        try {
            delete.deleteAutoSign(serverType, authenticationToken, autoSignId);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void digitalStamp(File file) {

    }

    public static Builder getBuilder() {
        return new Builder();
    }
}
