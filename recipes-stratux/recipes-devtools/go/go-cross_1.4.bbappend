do_compile() {
  ## Setting `$GOBIN` doesn't do any good, looks like it ends up copying binaries there.
  export GOROOT_FINAL="${SYSROOT}${libdir}/go"

  export GOHOSTOS="linux"
  export GOOS="linux"

  export GOARCH="${TARGET_ARCH}"
  if [ "${TARGET_ARCH}" = "x86_64" ]; then
    export GOARCH="amd64"
  fi
  if [ "${TARGET_ARCH}" = "i586" ]; then
    export GOARCH="386"
  fi
  if [ "${TARGET_ARCH}" = "arm" ]
  then
    if [ `echo ${TUNE_PKGARCH} | cut -c 1-7` = "cortexa" ]
    then
      echo GOARM 7
      export GOARM="7"
    fi
  fi

  export CGO_ENABLED="1"
  ## TODO: consider setting GO_EXTLINK_ENABLED

  export CC="${BUILD_CC}"
  export CC_FOR_TARGET="${CC}"
  export CXX_FOR_TARGET="${CXX}"
  export GO_CCFLAGS="${HOST_CFLAGS}"
  export GO_LDFLAGS="${HOST_LDFLAGS}"

  cd src && sh -x ./make.bash

  ## The result is `go env` giving this:
  # GOARCH="amd64"
  # GOBIN=""
  # GOCHAR="6"
  # GOEXE=""
  # GOHOSTARCH="amd64"
  # GOHOSTOS="linux"
  # GOOS="linux"
  # GOPATH=""
  # GORACE=""
  # GOROOT="/home/build/poky/build/tmp/sysroots/x86_64-linux/usr/lib/cortexa8hf-vfp-neon-poky-linux-gnueabi/go"
  # GOTOOLDIR="/home/build/poky/build/tmp/sysroots/x86_64-linux/usr/lib/cortexa8hf-vfp-neon-poky-linux-gnueabi/go/pkg/tool/linux_amd64"
  ## The above is good, but these are a bit odd... especially the `-m64` flag.
  # CC="arm-poky-linux-gnueabi-gcc"
  # GOGCCFLAGS="-fPIC -m64 -pthread -fmessage-length=0"
  # CXX="arm-poky-linux-gnueabi-g++"
  ## TODO: test on C+Go project.
  # CGO_ENABLED="1"
}

