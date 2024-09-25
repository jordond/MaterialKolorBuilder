# MaterialKolor Builder

## TODO

- Host with Firebase
  - Builds will be pushed on PR for staging, and live for master
- MaterialKolor workflow
  - A PR opens on MaterialKolor
    - Trigger workflow on MKB that:
      - Clones the repo
      - Checks out the branch
      - Builds and deploys
      - Comments on the PR with the link