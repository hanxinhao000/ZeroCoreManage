## Native packages build environment

There are located build scripts and patches for compiling native executables
like QEMU and dependencies. Environment is being used as Docker image
containing the Ubuntu distribution, Android NDK standalone toolchain and
number of other utilities used during the build process.

Dockerfile: `./scripts/Dockerfile`

Container setup script: `./scripts/run-docker.sh`

### Usage

1. Start build environment shell:
   ```
   ./scripts/run-docker.sh
   ```
2. Compile package:
   ```
   ./build-package.sh -a all qemu-system
   ```

Produced JNI shared libraries will be placed to `./jniLibs` if no errors
happened.

To clean build environment, execute `./clean.sh`. Note that cleaning won't
affect directory `./jniLibs`.
