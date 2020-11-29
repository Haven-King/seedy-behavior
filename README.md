# seedy-behavior
Small library that allows custom dimensions to omit the "seed" parameter from both the generator and the biome source to use the world seed.

# Usage
Add the following to your top-level `repositories` block:
```groovy
maven { url "https://hephaestus.dev/release" }
```

And under `dependencies`:
```groovy
modImplementation "dev.hephaestus:seedy-behavior:${project.seedy_version}"
include "dev.hephaestus:seedy-behavior:${project.seedy_version}"
```

Then, simply omit the "seed" parameter from your dimension json file!
