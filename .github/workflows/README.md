# Workflows

> 🔒 **Security Notice:** As this is a public repository, all automated triggers (on push/pull request) have been disabled to prevent PR hijacking and supply-chain vulnerabilities. Workflows must be triggered manually by organization members.

### Maven Tests
- Definition
    - run `mvn clean install -B -ntp -Dgpg.skip=true`
- Trigger
    - workflow_dispatch (manual trigger) via the Actions tab. Select your target branch/PR before running.

### Maven Release
- Definition
    - run `mvn release:prepare`
    - run `mvn release:perform`
    - push the release on Sonatype Central Portals
- Trigger
    - workflow_dispatch (manual trigger) via the Actions tab. **Note: Releases should typically be executed from the `master` branch.**

📖 For full documentation on how these workflows and configuration files work, please refer to the [urbanairship/java-env repository](https://github.com/urbanairship/java-env#github-actions-and-workflows).