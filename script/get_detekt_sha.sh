DETEKT_RELEASE_TAG="v1.23.3"

# Hole die JSON-Antwort der API
gh api graphql --field tagName=$DETEKT_RELEASE_TAG --raw-field query='
  query getReleaseAssetDownloadUrl($tagName: String!) {
    repository(name: "detekt", owner: "detekt") {
      release(tagName: $tagName) {
        releaseAssets(first: 1) {  # Hole bis zu 10 Assets, um zu debuggen
          nodes {
            name
            downloadUrl
          }
        }
        tagCommit {
          oid
        }
      }
    }
  }
' > gh_response.json

# Gib die gesamte JSON-Antwort aus, um zu debuggen
cat gh_response.json

# Verarbeite die JSON-Antwort, um den SHA-Wert zu erhalten
DETEKT_RELEASE_SHA=$(jq --raw-output '.data.repository.release.tagCommit.oid' gh_response.json)
echo "Detekt Release SHA: $DETEKT_RELEASE_SHA"

# Verarbeite die JSON-Antwort, um den Download-URL zu erhalten
DETEKT_DOWNLOAD_URL=$(jq --raw-output '.data.repository.release.releaseAssets.nodes[0].downloadUrl' gh_response.json)
echo "download_url=$DETEKT_DOWNLOAD_URL"
