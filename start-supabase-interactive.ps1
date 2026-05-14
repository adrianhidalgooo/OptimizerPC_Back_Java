$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

$env:DATABASE_URL = "jdbc:postgresql://aws-0-eu-west-1.pooler.supabase.com:5432/postgres?sslmode=require"
$env:DATABASE_USERNAME = "postgres.udcprcvxzaknbkmikayc"
$env:DATABASE_PASSWORD = Read-Host "Supabase database password"
$env:JWT_SECRET = "OptimizerPC_TFG_2026_clave_super_larga_1234567890"

.\mvnw.cmd spring-boot:run
