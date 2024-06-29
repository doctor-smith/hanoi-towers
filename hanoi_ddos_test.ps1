$numberOfRequests = 50
$url = "http://localhost:8080"

for ($i = 1; $i -le $numberOfRequests; $i++) {
    try {
        $response = Invoke-WebRequest -Uri $url -UseBasicParsing
        Write-Output "Request $($i): Status $($response.StatusCode)"
    } catch {
        Write-Output "Request $($i): Failed - $($_.Exception.Message)"
    }

    Start-Sleep -Milliseconds (1000 / $numberOfRequests)
}
