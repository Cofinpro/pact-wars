directory=$(dirname "$0")
gitsha=$(git rev-parse HEAD)

${directory}/../../pact-cli/osx/bin/pact-broker publish \
   ${directory}/build/pacts \
   --broker-base-url=http://localhost:8005 \
   --consumer-app-version=${gitsha} \
   --tag-with-git-branch
