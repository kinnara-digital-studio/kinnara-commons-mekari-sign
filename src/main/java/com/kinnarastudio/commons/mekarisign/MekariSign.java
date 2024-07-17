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
    public MekariSign(AuthenticationToken authenticationToken) {
        this.serverType = authenticationToken.getServerType();
        this.authenticationToken = authenticationToken;
    }

    /**
     * Implementation of Mekari Sign's global sign
     *
     * @see <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#c788f84c-9228-4eb9-a55f-a043f9d9019a">Global - Request Sign</a>
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

    /**
     * Implementation of Mekari Sign's global sign
     * 
     * @param file              File to upload
     * @param signers           List of signers information
     * @throws RequestException 
     */
    public void globalSign(File file, RequestSigner[] signers) throws RequestException {
        try (final InputStream is = Files.newInputStream(file.toPath())) {
            final String filename = file.getName();
            globalSign(is, filename, signers);
        } catch (IOException e) {
            throw new RequestException(e);
        }
    }

    /**
     * Implementation of Mekari Sign's global sign
     * 
     * @param inputStream       Stream of file input
     * @param filename          Name of the file
     * @param signers           List of signers information
     * @throws RequestException
     */
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

    /**
     * Implementation of Mekari Sign's PSrE Sign
     * 
     * @see <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#d4cfb7a8-cf05-439e-9e0c-6cf59084edf7">PSrE - Request Sign</a>
     *
     * @param file                  File to upload
     * @param reqSigner             List of signer information
     * @throws RequestException
     * @throws ParseException
     */
    public void psreSign(File file, RequestSigner reqSigner) throws RequestException, ParseException {
        psreSign(file, new RequestSigner[]{reqSigner});
    }

    /**
     * Implementation of Mekari Sign's PSrE Sign
     * 
     * @param inputStream       Stream of file input
     * @param filename          Name of the file
     * @param signer            List of signer information
     * @throws RequestException
     * @throws ParseException
     */
    public void psreSign(InputStream inputStream, String filename, RequestSigner signer) throws RequestException, ParseException {
        psreSign(inputStream, filename, new RequestSigner[]{signer});
    }

    /**
     * Implementation of Mekari Sign's PSrE Sign
     * 
     * @param file                  File to upload
     * @param signers               List of signer information
     * @throws RequestException
     * @throws ParseException
     */
    public void psreSign(File file, RequestSigner[] signers) throws RequestException, ParseException {
        try (final InputStream is = Files.newInputStream(file.toPath())) {

            final String filename = file.getName();
            psreSign(is, filename, signers);

        } catch (IOException e) {
            throw new RequestException(e);
        }
    }

    /**
     * Implementation of Get Document List on Mekari Sign's
     * 
     * @see <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#a4d5f4f7-1324-4535-8f16-4321ec6b4875">Get Document Lists</a>
     * 
     * @param page                  Page that want's to be accesed
     * @param limit                 Limit per page
     * @param category              Document category (global, psre)
     * @param status                Document signing status (in_progress, completed, voided, declined)
     * @param stamping              Document stamping status (in_progress, none, pending, failed, success)
     * @throws RequestException
     * @throws ParseException
     */
    public GetDocumentListBody getDoc(int page, int limit, DocumentCategory category, SigningStatus status, StampingStatus stamping) throws RequestException, ParseException {
        DocumentListService docListGet = DocumentListService.getInstance();
        return docListGet.requestDocs(serverType, authenticationToken, page, limit, category, status, stamping);
    }

    /**
     * Implementation of Get Status from the Current User on Mekari Sign's
     * 
     * @see <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#bcd5c3fb-0c0b-46fe-a83c-1c049f08d38a">Current User</a>
     * 
     * @throws RequestException
     * @throws ParseException
     */
    public void getProfile() throws RequestException, ParseException {
        UserProfileService userProfileService = UserProfileService.getInstance();
        userProfileService.requestProfile(serverType, authenticationToken);
    }

    /**
     * Implementation of Get Document Detail on Mekari Sign's
     * 
     * @see <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#dc3fe047-f0b6-4c93-9e5f-fbb640b2a007">Get Document Detail</a>
     * 
     * @param id                ID of the document that want be searched
     * @throws RequestException
     * @throws ParseException
     */
    public ResponseData getDocDetail(String id) throws RequestException, ParseException {
        DocumentDetailService docListDetail = DocumentDetailService.getInstance();
        return docListDetail.requestDocs(serverType, authenticationToken, id);
    }

    /**
     * Implementation of Download Document on Mekari Sign's
     * 
     * @see <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#693a5e1e-97c3-447b-8884-81e42766e329">Download Document File</a>
     * 
     * @param id            ID of the document that want be downloaded
     * @param fileOutput    File to downloaded
     * @throws RequestException
     */
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

    /**
     * Implementation of Download Document on Mekari Sign's
     * 
     * @param id                    ID of the document that want be downloaded
     * @param outputStream          Stream of output file
     * @throws RequestException
     */
    public void downloadDoc(String id, OutputStream outputStream) throws RequestException {
        if (outputStream == null) {
            throw new RequestException("Error writing output");
        }

        DocumentDownloader documentDownloader = DocumentDownloader.getInstance();
        documentDownloader.downloadFile(serverType, authenticationToken, id, outputStream);
    }

    /**
     * Implementation of Mekari Sign's PSrE Sign
     * 
     * @param inputStream           Stream of file input
     * @param filename              Name of the file
     * @param signers               List of signer information
     * @throws RequestException
     * @throws ParseException
     */
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

    /**
     * Implementation of Mekari Sign's create auto-sign
     * 
     * @see <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#7c28af8b-7e7b-4482-90a6-0595c081369c">create auto-sign</a>
     * 
     * @param reqAutoSign       Requestor and signer emails
     * @throws RequestException
     */
    public void autoSign(ReqAutoSign reqAutoSign) throws RequestException {
        final AutoSignService autoSignService = AutoSignService.getInstance();
        try {
            autoSignService.requestAutoSign(serverType, authenticationToken, reqAutoSign);
        } catch (IOException e) {
            throw new RequestException(e);
        }
    }

    /**
     * Implementation of Mekari Sign's delete auto-sign
     * 
     * @see <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#b29b5845-dff1-4b32-8ddb-5b08ed314358">delete auto-sign</a>
     * 
     * @param autoSignId        Auto Sign ID
     * @throws RequestException
     */
    public void deleteAutoSign(String autoSignId) throws RequestException {
        final DeleteAutoSign delete = DeleteAutoSign.getInstance();
        try {
            delete.deleteAutoSign(serverType, authenticationToken, autoSignId);
        } catch (IOException e) {
            throw new RequestException(e);
        }
    }

    /**
     * Implementation of Mekari Sign's KYC
     * 
     * @see <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#40f5a676-7523-4bcb-b5e7-33c02bf5dfc1">KYC Invitation</a>
     * 
     * @param requestEKYC           Email, Callback, boolean send email
     * @throws RequestException
     */
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
