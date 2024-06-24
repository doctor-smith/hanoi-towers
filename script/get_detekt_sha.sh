DETEKT_RELEASE_TAG="v1.23.3"

# Hole die JSON-Antwort der API
gh api graphql --field tagName=$DETEKT_RELEASE_TAG --raw-field query='
  query getReleaseAssetDownloadUrl($tagName: String!) {
    repository(name: "detekt", owner: "detekt") {
      release(tagName: $tagName) {
        tagCommit {
          oid
        }
      }
    }
  }
' > gh_response.json

# Verarbeite die JSON-Antwort, um den SHA-Wert zu erhalten
DETEKT_RELEASE_SHA=$(jq --raw-output '.data.repository.release.tagCommit.oid' gh_response.json)
echo "Detekt Release SHA: $DETEKT_RELEASE_SHA"
