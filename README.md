# MOVILES_PRO - ExchangePro Android

App Android nativa para ExchangePro usando Kotlin, Jetpack Compose y arquitectura preparada para Firebase.

## Estado actual

- Pantallas y navegacion adaptadas del frontend web.
- Datos temporales en repositorios mock con forma parecida a Firestore.
- Dependencias Firebase listas: Auth, Firestore, Storage y Analytics.
- No incluye `google-services.json` real.

## Conectar Firebase

1. Registrar una app Android en Firebase con este package name:

```text
com.exchangepro.moviles
```

2. Descargar el archivo real `google-services.json`.
3. Colocarlo en:

```text
app/google-services.json
```

4. Activar en Firebase Console:

- Authentication
- Cloud Firestore
- Storage

La app compila sin `google-services.json`; cuando el archivo exista, Gradle aplicara el plugin de Google Services automaticamente.
