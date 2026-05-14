param(
    [Parameter(Mandatory = $true)]
    [string]$DatabaseUrl,

    [Parameter(Mandatory = $true)]
    [string]$DatabaseUsername,

    [Parameter(Mandatory = $true)]
    [string]$DatabasePassword,

    [Parameter(Mandatory = $true)]
    [string]$JwtSecret
)

$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

$env:DATABASE_URL = $DatabaseUrl
$env:DATABASE_USERNAME = $DatabaseUsername
$env:DATABASE_PASSWORD = $DatabasePassword
$env:JWT_SECRET = $JwtSecret

.\mvnw.cmd spring-boot:run
