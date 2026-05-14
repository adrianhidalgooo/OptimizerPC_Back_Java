# Configurar Supabase para Optimizer PC Backend

Este backend ya esta preparado para conectarse a Supabase/PostgreSQL.

## 1. Crear proyecto

1. Entra en `https://supabase.com`.
2. Crea un proyecto nuevo.
3. Guarda la password de la base de datos.
4. Espera a que el proyecto termine de crearse.

## 2. Obtener datos de conexion

En Supabase:

1. Entra en tu proyecto.
2. Pulsa `Connect`.
3. Busca `Session pooler`.
4. Copia los datos de host, user y password.

Normalmente veras algo parecido a:

```txt
postgres://postgres.PROJECT_REF:PASSWORD@aws-0-region.pooler.supabase.com:5432/postgres
```

Para Spring Boot lo usaremos asi:

```txt
DATABASE_URL=jdbc:postgresql://aws-0-region.pooler.supabase.com:5432/postgres?sslmode=require
DATABASE_USERNAME=postgres.PROJECT_REF
DATABASE_PASSWORD=PASSWORD
```

## 3. Arrancar el backend

Desde PowerShell:

```powershell
cd C:\Users\ahida\Desktop\OptimizerPC_Back_Java

.\run-supabase.ps1 `
  -DatabaseUrl "jdbc:postgresql://TU_HOST:5432/postgres?sslmode=require" `
  -DatabaseUsername "postgres.TU_PROJECT_REF" `
  -DatabasePassword "TU_PASSWORD" `
  -JwtSecret "una_clave_larga_de_minimo_32_caracteres"
```

Si arranca correctamente, abre:

```txt
http://localhost:8080/
```

Deberias ver:

```json
{
  "name": "Optimizer PC Java API",
  "status": "ok"
}
```

## 4. Usuario inicial

Cuando Spring Boot arranca conectado a Supabase, crea automaticamente:

```txt
username: admin
password: admin123
```

Tambien crea categorias y productos iniciales.

