package burp

import org.json.JSONObject
import org.json.JSONTokener

class BurpExtender: IBurpExtender {
    override fun registerExtenderCallbacks(callbacks: IBurpExtenderCallbacks) {
        callbacks.setExtensionName("Brute password")
        callbacks.registerIntruderPayloadProcessor(IntruderPayloadProcessor(callbacks))
    }
}


class IntruderPayloadProcessor(val callbacks: IBurpExtenderCallbacks): IIntruderPayloadProcessor {
    override val processorName: String
        get() = "Set platform auth password"

    override fun processPayload(currentPayload: ByteArray, originalPayload: ByteArray, baseValue: ByteArray): ByteArray {
        val jsonString = callbacks.saveConfigAsJson("project_options.connections.platform_authentication")
        val root = JSONObject(JSONTokener(jsonString))
        val credentials = root.getJSONObject("project_options")
                .getJSONObject("connections")
                .getJSONObject("platform_authentication")
                .getJSONArray("credentials")
                .getJSONObject(0)
        credentials.put("password", String(currentPayload, Charsets.ISO_8859_1))
        callbacks.printOutput(root.toString())
        callbacks.loadConfigFromJson(root.toString())
        return currentPayload
    }
}
