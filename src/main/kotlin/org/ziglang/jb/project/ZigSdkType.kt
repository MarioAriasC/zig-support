package org.ziglang.jb.project

import com.intellij.openapi.projectRoots.*
import org.jdom.Element

class ZigSdkType : SdkType("Zig") {
    override fun saveAdditionalData(p0: SdkAdditionalData, p1: Element) {
        TODO("Not yet implemented")
    }

    override fun suggestHomePath(): String? {
        TODO("Not yet implemented")
    }

    override fun isValidSdkHome(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun suggestSdkName(p0: String?, p1: String): String {
        TODO("Not yet implemented")
    }

    override fun createAdditionalDataConfigurable(p0: SdkModel, p1: SdkModificator): AdditionalDataConfigurable? {
        TODO("Not yet implemented")
    }

    override fun getPresentableName(): String = "Zig SDK"
}