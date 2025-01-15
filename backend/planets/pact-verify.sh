directory=$(dirname "$0")
gitsha=$(git rev-parse HEAD)
gitbranch=$(git rev-parse --abbrev-ref HEAD)

${directory}/gradlew test \
    --tests 'de.cofinpro.planets.controllers*' \
    -Dpact.provider.tag="${gitbranch}" \
    -Dpact.provider.version=${gitsha} \
    -Dpact.verifier.publishResults=true
