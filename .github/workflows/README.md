# Workflows

### Maven Tests
- Definition
    - run `mvn clean install`
- Trigger
    - on pull request / push to branch

### Maven Release
- Definition
    - run `mvn clean install`
    - run `mvn release:prepare`
    - run `mvn release:perform`
    - push the release on gcs ua-ops-artifacts/airship-maven-artifacts
- Trigger
    - on merge to Main/Master
    - workflow_dispatch (manual trigger)

📖 For full documentation on how these workflows and configuration files work, please refer to the [urbanairship/java-env repository](https://github.com/urbanairship/java-env#github-actions-and-workflows).