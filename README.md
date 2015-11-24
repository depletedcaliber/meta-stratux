# meta-stratux
Yocto layer for building a stratux image


## How to use
### Acquire Edison Source and extract
```
wget -c http://downloadmirror.intel.com/25028/eng/edison-src-ww25.5-15.tgz
tar xzf edison-src-ww25.5-15.tgz
```

### Acquire OpenEmbedded recipes and switch to dizzy-next
```
cd edison-src/meta-intel-edison
git clone https://github.com/openembedded/meta-openembedded.git
cd meta-openembedded
git checkout dizzy-next
```

### Acquire meta-golang layer (parallel folder to edison-src)
```
git clone https://github.com/errordeveloper/oe-meta-go.git
```

### Acquire meta-stratux layer (parallel folder to eidson-src)
```
git clone https://github.com/depletedcaliber/meta-stratux.git
```

### Use makefile to setup build environment (from edison-src directory)
```
cd edison-src
make setup
```

### configure config/bblayers.conf to add the meta-golang and meta-stratux layers.
```
cd edison-src/out/linux64/build/conf
<favorite text editor> bblayers.conf   *** See example bblayers.conf-example
```

### Build edison image
```
cd edison-src
make edison-image
```

### Flash edison image 
Files necessary to flash are output to edison-src/out/linux64/build/toFlash. Follow instructions in edison doc acording to the platform that you are going to flash from.




## Notes

This particular image as is contains modifications to the GPS in the following patch. The patch skips the initialization as my GPS has onboard flash that it saves the configuration settings too. Remove this patch to restore regular ry835ai operation.
```
recipes-stratux/stratux/files/0003-gps-hardcoding.patch
```
