DESCRIPTION = "This is a Yocto build recipe for Stratux for installation on the Stratux Yocto image for intel edison"
SECTION = "Stratux"
HOMEPAGE = "http://github.com/cyoung/stratux/"

DEPENDS = "go-cross dump1090 dump978 rtlsdr libusb"
RDEPENDS${PN} = "libdump978"



export GOARCH="386"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"


#SRCREV = "${AUTOREV}"
SRCREV = "25b8bf040217306c9c015a5dc3c9d798358d2355"

SRC_URI += "git://github.com/cyoung/stratux.git;branch=powersave"

SRC_URI += "file://stratux.service"
SRC_URI += "file://start_uat.sh"
SRC_URI += "file://write_leases.sh"
SRC_URI += "file://0002-cpu-temp-sensor.patch"
SRC_URI += "file://0003-gps-hardcoding.patch"
SRC_URI += "file://0004-dhcp-leases.patch"

S = "${WORKDIR}/git"
export GOPATH="${S}/gopath"

LICENSE = "CYOUNG-1"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=76518b243b02e8b9af84baf8c98fb915"


inherit systemd
SYSTEMD_SERVICE_${PN} = "stratux.service"
SYSTEMD_AUTO_ENABLE = "enable"


do_compile () {

export CGO_CXXFLAGS="$CXXFLAGS"
export CGO_CFLAGS="$CFLAGS"
export CGO_CPPFLAGS="$CPPFLAGS"
export CGO_LDFLAGS="$LDFLAGS"
export CGO_ENABLED="1"
export CC_FOR_TARGET="$CC" 
export CXX_FOR_TARGET="$CXX"


    go get -t -d -v ./main
    go build main/gen_gdl90.go main/traffic.go main/ry835ai.go main/network.go main/managementinterface.go main/sdr.go main/uibroadcast.go

}

do_install() {
    install -m 0755 -d ${D}${bindir} ${D}${sysconfdir}/init.d ${D}${sbindir}

    install -m 0755 ${S}/gen_gdl90 ${D}${bindir}   
    install -m 0755 ${WORKDIR}/start_uat.sh ${D}${bindir}
    install -m 0755 ${WORKDIR}/write_leases.sh ${D}${bindir}
   
    install -m 0755 ${S}/init.d-stratux ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/start_stratux.sh ${D}${sbindir}
   
        install -m 0755 -d ${D}/var/www ${D}/var/www/css ${D}/var/www/js ${D}/var/www/img ${D}/var/www/maui ${D}/var/www/maui/js ${D}/var/www/maui/css ${D}/var/www/maui/fonts ${D}/var/www/plates ${D}/var/www/plates/js ${D}/var/lib/dhcp
	install -m 0755 ${S}/web/css/*.css ${D}/var/www/css
	install -m 0755 ${S}/web/js/main.js ${D}/var/www/js
	install -m 0755 ${S}/web/js/addtohomescreen.min.js ${D}/var/www/js
	install -m 0755 ${S}/web/js/j3di-all.min.js ${D}/var/www/js
	install -m 0755 ${S}/web/img/logo*.png ${D}/var/www/img
	install -m 0755 ${S}/web/img/screen*.png ${D}/var/www/img
	install -m 0755 ${S}/web/img/world.png ${D}/var/www/img
	install -m 0755 ${S}/web/maui/js/angular-ui-router.min.js ${D}/var/www/maui/js
	install -m 0755 ${S}/web/maui/js/mobile-angular-ui.min.js ${D}/var/www/maui/js
	install -m 0755 ${S}/web/maui/js/angular.min.js ${D}/var/www/maui/js
	install -m 0755 ${S}/web/maui/js/mobile-angular-ui.gestures.min.js ${D}/var/www/maui/js
	install -m 0755 ${S}/web/maui/js/mobile-angular-ui.core.min.js ${D}/var/www/maui/js
	install -m 0755 ${S}/web/maui/css/mobile-angular-ui-hover.min.css ${D}/var/www/maui/css
	install -m 0755 ${S}/web/maui/css/mobile-angular-ui-desktop.min.css ${D}/var/www/maui/css
	install -m 0755 ${S}/web/maui/css/mobile-angular-ui-base.min.css ${D}/var/www/maui/css
	install -m 0755 ${S}/web/maui/fonts/fontawesome-webfont.woff ${D}/var/www/maui/fonts
	install -m 0755 ${S}/web/plates/*.html ${D}/var/www/plates
	install -m 0755 ${S}/web/plates/js/*.js ${D}/var/www/plates/js
	install -m 0755 ${S}/web/index.html ${D}/var/www

    if ${@base_contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -d ${D}/${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/stratux.service ${D}${systemd_unitdir}/system
    fi


}
