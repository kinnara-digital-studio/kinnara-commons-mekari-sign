# Mekari Sign Java API Implementation

## Description
Mekari eSign API provides a broad range of functionality to help you build documents, stamp
meterai, and sign documents. Mekari Sign API is organized around REST. Our API has predictable resource-oriented URLs and uses standard HTTP response codes, authentication, and verbs. Mekari eSign is a digital signature. Digital signature is a secure, electronic way to confirm the authenticity and integrity of a document or message using encryption. Several features are available in the implementation of the mekari esign application, namely Global Sign, PSrE Sign, eKYC, and Auto Sign.

## Java Version
11.0

## Configuration
If you want to use this Mekari Sign Java API, you will need :
- Maven 4.0.0
- ``` mvn -DdistributionTargetDir="$HOME/app/maven/apache-maven-4.0.x-SNAPSHOT" clean package ```

## Examples
Here are the usage of some features in Mekari eSign API :
- Global Sign Request :
```
final MekariSign mekariSign = MekariSign.getBuilder()
      .setClientId(clientId)
      .setClientSecret(clientSecret)
      .setServerType(ServerType.SANDBOX)
      .setSecretCode(code)
      .build();

mekariSign.globalSign(file, signer);
```

- Auto Sign Request :
```
final MekariSign mekariSign = MekariSign.getBuilder()
      .setClientId(clientId)
      .setClientSecret(clientSecret)
      .setServerType(ServerType.SANDBOX)
      .setSecretCode(code)
      .build();

mekariSign.autoSign(req);
```

- Delete Auto Sign Request :
```
  final MekariSign mekariSign = MekariSign.getBuilder()
     .setClientId(clientId)
      .setClientSecret(clientSecret)
      .setServerType(ServerType.SANDBOX)
      .setSecretCode(code)
      .build();

mekariSign.deleteAutoSign(id);
```

- PSrE Sign Request :
```
final MekariSign mekariSign = MekariSign.getBuilder()
    .setClientId(clientId)
    .setClientSecret(clientSecret)
    .setServerType(ServerType.SANDBOX)
    .setSecretCode(code)
    .build();

mekariSign.psreSign(file, signer);
```

- Download File :
```
final MekariSign mekariSign = MekariSign.getBuilder()
      .setClientId(clientId)
      .setClientSecret(clientSecret)
      .setServerType(ServerType.SANDBOX)
      .setSecretCode(code)
      .build();

mekariSign.downloadDoc("01ec84e4-f8b4-449b-9429-ffff8c1a764b", file);
```

- Document List Retrieval :
```
final MekariSign mekariSign = MekariSign.getBuilder()
     .setClientId(clientId)
     .setClientSecret(clientSecret)
     .setServerType(ServerType.SANDBOX)
     .setSecretCode(code)
     .build();

mekariSign.getDoc(1, 8, DocumentCategory.PSRE, null, null);;
```

- Document List Detail :
```
final MekariSign mekariSign = MekariSign.getBuilder()
      .setClientId(clientId)
      .setClientSecret(clientSecret)
      .setServerType(ServerType.SANDBOX)
      .setSecretCode(code)
      .build();

mekariSign.getDocDetail("01ec84e4-f8b4-449b-9429-ffff8c1a764b");;
```

## References
Here is the online documentation from Mekari eSign that you can use to try out the Mekari eSign API https://documenter.getpostman.com/view/21582074/2s93K1oecc#intro.

## Contributors Guide
- Fork kinnara-commons-mekari-sign
- Create your feature branch ($ git checkout -b my-new-feature).
- Make contributions
- Commit your changes ($ git commit -am 'Add some feature').
- Push to the branch ($ git push origin my-new-feature).
- Create new Pull Request.
