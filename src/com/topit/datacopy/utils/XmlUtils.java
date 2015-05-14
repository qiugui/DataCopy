package com.topit.datacopy.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.topit.datacopy.config.Constants;

/**
 * 
 * @ClassName: XMLParser
 * @Description: 读写配置文件
 * @author gaodachuan
 * @date 2015年3月10日 上午11:36:06
 *
 */
public class XmlUtils {
	// 生成的配置文件的保存位置
	private final String configPath = "./config.xml";
	File file = new File(configPath);

	private DocumentBuilder getBuilder() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		builder = factory.newDocumentBuilder();
		return builder;

	}

	// 初始化构建xml文件
	public void createXml() throws Exception {

		file.createNewFile();
		Document document = getBuilder().newDocument();

		// 创建根节点
		Element root = document.createElement(Constants.ROOT);
		document.appendChild(root);

		/* 创建一个完成的节点，sourcedb */
		Element sourcedb = document
				.createElement(Constants.DBConnection.SOURCEDB);
		sourcedb.appendChild(document.createElement(Constants.DBConnection.IP));
		sourcedb.appendChild(document
				.createElement(Constants.DBConnection.PORT));
		sourcedb.appendChild(document
				.createElement(Constants.DBConnection.DBNAME));
		sourcedb.appendChild(document
				.createElement(Constants.DBConnection.USER));
		sourcedb.appendChild(document
				.createElement(Constants.DBConnection.PASSWORD));
		root.appendChild(sourcedb);

		/* 创建第二个节点，targetdb */
		Element targetdb = document
				.createElement(Constants.DBConnection.TARGETDB);
		targetdb.appendChild(document.createElement(Constants.DBConnection.IP));
		targetdb.appendChild(document
				.createElement(Constants.DBConnection.PORT));
		targetdb.appendChild(document
				.createElement(Constants.DBConnection.DBNAME));
		targetdb.appendChild(document
				.createElement(Constants.DBConnection.USER));
		targetdb.appendChild(document
				.createElement(Constants.DBConnection.PASSWORD));
		root.appendChild(targetdb);

		/* 创建第三个节点，autoruntimes */
		Element autoruntimes = document
				.createElement(Constants.AutoTask.AUTORUNTIMES);
		autoruntimes.appendChild(document
				.createElement(Constants.AutoTask.AUTORUNTIME1));
		autoruntimes.appendChild(document
				.createElement(Constants.AutoTask.AUTORUNTIME2));
		autoruntimes.appendChild(document
				.createElement(Constants.AutoTask.AUTORUNTIME3));
		root.appendChild(autoruntimes);

		/* 创建第四个节点，users */
		Element user = document.createElement(Constants.User.USER);
		user.appendChild(document.createElement(Constants.User.USERNAME));
		user.appendChild(document.createElement(Constants.User.PASSWORD));
		root.appendChild(user);

		/**
		 * 创建第五个节点 lastCopyTime
		 */
		Element time = document.createElement(Constants.LastCopyTimeNode);
		root.appendChild(time);
		writeDom2File(document);

	}

	// 将DOM对象document写入到xml文件中
	private void writeDom2File(Document doc) throws Exception {
		FileOutputStream fio = null;
		PrintWriter pw = null;

		try {
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			fio = new FileOutputStream(file);
			pw = new PrintWriter(fio);
			StreamResult result = new StreamResult(pw);
			transformer.transform(new DOMSource(doc), result); // 关键转换
		} finally {
			fio.close();
			pw.close();
		}
	}

	public Element getElementByTag(String tagName) throws Exception {
		if (!file.exists()) {
			createXml();
		}
		// 通过DocumentBuilder创建doc的文档对象
		Document docs = getBuilder().parse(file);
		// 创建XPath
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = (NodeList) xPath.evaluate("//" + tagName, docs,
				XPathConstants.NODESET);
		Element element = (Element) nodeList.item(0);
		return element;
	}

	public void updateXML(String parentTag, String targetTag, String tagetValue)
			throws Exception {

		if (!file.exists()) {
			createXml();
		}
		Document doc = getBuilder().parse(file);
		doc.normalize();
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = (NodeList) xPath.evaluate("//" + parentTag, doc,
				XPathConstants.NODESET);
		Element parent = (Element) nodeList.item(0);
		Element target = (Element) parent.getElementsByTagName(targetTag).item(
				0);
		target.setTextContent(tagetValue);
		writeDom2File(doc);

	}

}
