DESCRIPTION = "This is a Yocto build recipe for Stratux for installation on the Stratux Yocto image for intel edison. This recipe is for the dump1090 portion."
SECTION = "Stratux"
HOMEPAGE = "http://github.com/cyoung/stratux/"

DEPENDS = "rtlsdr"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PACKAGES = "${PN}"


SRCREV = "${AUTOREV}"
SRC_URI += "git://github.com/cyoung/stratux.git"
SRC_URI += "file://LICENSE"

INSANE_SKIP_${PN} = "installed-vs-shipped"

S = "${WORKDIR}/git/dump1090"

LICENSE = "SSANFILIPPOv1"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=89ac946758d770ea31afde2f23dd98ca"



do_compile() {
    oe_runmake
}

do_install() {
    install -m 0755 -d ${D}${bindir}
    install -m 0755 ${S}/dump1090  ${D}${bindir}
}
