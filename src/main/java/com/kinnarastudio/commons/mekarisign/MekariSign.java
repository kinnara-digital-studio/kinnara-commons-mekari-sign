package com.kinnarastudio.commons.mekarisign;

import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.*;
import com.kinnarastudio.commons.mekarisign.service.*;

import java.io.*;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Base64;

/**
 * Mekari Sign main class
 */
public class MekariSign {
    public final static int BYTE_ARRAY_BUFFER_SIZE = 4096;

    private final ServerType serverType;
    private final AuthenticationToken authenticationToken;

    /**
     *
     * @param serverType            {@link ServerType}
     * @param authenticationToken   {@link AuthenticationToken}
     */
    public MekariSign(ServerType serverType, AuthenticationToken authenticationToken) {
        this.serverType = serverType;
        this.authenticationToken = authenticationToken;
    }

    /**
     * Implementation of Mekari Sign's global sign
     *
     * @see <a href="https://">docs</a>
     *
     * @param file              File to upload
     * @param signer            Signer's information
     * @throws RequestException
     */
    public void globalSign(File file, RequestSigner signer) throws RequestException {
        globalSign(file, new RequestSigner[]{signer});
    }

    /**
     * Implementation of Mekari Sign's global sign
     *
     * @param inputStream       Stream of file input
     * @param filename          Name of file
     * @param signer            Signer's information
     * @throws RequestException
     */
    public void globalSign(InputStream inputStream, String filename, RequestSigner signer) throws RequestException {
        globalSign(inputStream, filename, new RequestSigner[]{signer});
    }

    public void globalSign(File file, RequestSigner[] signers) throws RequestException {
        try (final InputStream is = Files.newInputStream(file.toPath())) {
            final String filename = file.getName();
            globalSign(is, filename, signers);
        } catch (IOException e) {
            throw new RequestException(e);
        }
    }

    public void globalSign(InputStream inputStream, String filename, RequestSigner[] signers) throws RequestException {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            final Base64.Encoder encoder = Base64.getEncoder();
            final byte[] buffer = new byte[BYTE_ARRAY_BUFFER_SIZE];
            int len;
            while ((len = inputStream.read(buffer)) >= 0) {
                bos.write(buffer, 0, len);
            }

            final String doc = encoder.encodeToString(bos.toByteArray());
            final GlobalSigner globalSigner = GlobalSigner.getInstance();

            globalSigner.requestSign(serverType, authenticationToken, new SignRequest(filename, doc, signers));

        } catch (IOException e) {
            throw new RequestException(e);
        }
    }

    public void psreSign(File file, RequestSigner reqSigner) throws RequestException, ParseException {
        psreSign(file, new RequestSigner[]{reqSigner});
    }

    public void psreSign(InputStream inputStream, String filename, RequestSigner signer) throws RequestException, ParseException {
        psreSign(inputStream, filename, new RequestSigner[]{signer});
    }

    public void psreSign(File file, RequestSigner[] signers) throws RequestException, ParseException {
        try (final InputStream is = Files.newInputStream(file.toPath())) {

            final String filename = file.getName();
            psreSign(is, filename, signers);

        } catch (IOException e) {
            throw new RequestException(e);
        }
    }

    public void getDoc(int page, int limit, DocumentCategory category, SigningStatus status, StampingStatus stamping) throws RequestException, ParseException {
        DocumentListService docListGet = DocumentListService.getInstance();
        docListGet.requestDocs(serverType, authenticationToken, page, limit, category, status, stamping);
    }

    public void getProfile() throws RequestException, ParseException {
        UserProfileService userProfileService = UserProfileService.getInstance();
        userProfileService.requestProfile(serverType, authenticationToken);
    }

    public void getDocDetail(String id) throws RequestException, ParseException {
        DocumentDetailService docListDetail = DocumentDetailService.getInstance();
        docListDetail.requestDocs(serverType, authenticationToken, id);
    }

    public void downloadDoc(String id, File fileOutput) throws RequestException {
        if (fileOutput == null || !fileOutput.canWrite()) {
            throw new RequestException("Error writing file");
        }

        try (final OutputStream os = Files.newOutputStream(fileOutput.toPath())) {

            downloadDoc(id, os);

        } catch (IOException e) {
            throw new RequestException(e);
        }
    }

    public void downloadDoc(String id, OutputStream outputStream) throws RequestException {
        if (outputStream == null) {
            throw new RequestException("Error writing output");
        }

        DocumentDownloader documentDownloader = DocumentDownloader.getInstance();
        documentDownloader.downloadFile(serverType, authenticationToken, id, outputStream);
    }

    public void psreSign(InputStream inputStream, String filename, RequestSigner[] signers) throws RequestException, ParseException {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            final Base64.Encoder encoder = Base64.getEncoder();
            final byte[] buffer = new byte[BYTE_ARRAY_BUFFER_SIZE];
            int len;
            while ((len = inputStream.read(buffer)) >= 0) {
                bos.write(buffer, 0, len);
            }

            final String doc = encoder.encodeToString(bos.toByteArray());
            final PSrESigner psre = PSrESigner.getInstance();

            psre.requestSign(serverType, authenticationToken, new SignRequest(filename, doc, signers));

        } catch (IOException e) {
            throw new RequestException(e);
        }
    }

    public void autoSign(ReqAutoSign reqAutoSign) throws RequestException {
        final AutoSignService autoSignService = AutoSignService.getInstance();
        try {
            autoSignService.requestAutoSign(serverType, authenticationToken, reqAutoSign);
        } catch (IOException e) {
            throw new RequestException(e);
        }
    }

    public void deleteAutoSign(String autoSignId) throws RequestException {
        final DeleteAutoSign delete = DeleteAutoSign.getInstance();
        try {
            delete.deleteAutoSign(serverType, authenticationToken, autoSignId);
        } catch (IOException e) {
            throw new RequestException(e);
        }
    }

    public void requestEKYC(RequestKYC requestEKYC) throws RequestException{
        final KYC request = KYC.getInstance();
        try {
            request.requestKYC(serverType, authenticationToken, requestEKYC);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void digitalStamp(File file) {

    }

    /**
     * Get MekariSign builder
     *
     * @return  {@link Builder}
     */
    public static Builder getBuilder() {
        return new Builder();
    }
}
