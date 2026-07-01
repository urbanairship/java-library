# Workflows

This repo (`java-library-dev`) is the **private development mirror** of the public
[urbanairship/java-library](https://github.com/urbanairship/java-library) repository.
All CI/CD runs here; the public repo is updated automatically after each release.

---

### Maven Tests
- Definition
  - run `mvn clean install` (GPG skipped)
- Trigger
  - on pull request
  - workflow_dispatch (manual)

### Sonatype Release
- Definition
  - run `mvn release:prepare` + `release:perform`
  - sign artifacts with GPG and publish to [Sonatype Central Portal](https://central.sonatype.com/publishing) (`autoPublish: false` — manual approval required after the workflow completes)
  - push release commits + tag to `java-library-dev` (this repo)
  - sync master + tag to `java-library` (public repo)
- Trigger
  - workflow_dispatch (manual, master branch only)

---

### Required secrets

| Secret | Used by |
|--------|---------|
| `JAVA_BUILD_SA_KEY` | Maven Tests, Sonatype Release (Java/Maven toolchain) |
| `JAVA_BUILD_GH_TOKEN` | Maven Tests, Sonatype Release (checkout + push to -dev) |
| `SONATYPE_USERNAME` | Sonatype Release |
| `SONATYPE_PASSWORD` | Sonatype Release |
| `SONATYPE_GPG_PRIVATE_KEY` | Sonatype Release |
| `SONATYPE_GPG_PASSPHRASE` | Sonatype Release |
| `JAVA_LIBRARY_PUBLIC_TOKEN` | Sonatype Release (push to public repo) — fine-grained PAT, `Contents: write` on `urbanairship/java-library` only |

---

### Release flow

```
1. Merge PR to master on java-library-dev
2. Trigger "Sonatype Release" workflow manually
3. Workflow releases to Sonatype Central (staged, not yet public)
4. Approve bundle on https://central.sonatype.com/publishing
5. java-library (public) is automatically synced with the release commits + tag
```

📖 For full documentation on how these workflows and configuration files work, please refer to the [urbanairship/java-env repository](https://github.com/urbanairship/java-env#github-actions-and-workflows).
