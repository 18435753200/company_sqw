package com.mall.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.mall.pay.common.PayConstant.Global;
import com.mall.pay.po.alipay.AlipayQueryResult;

public class JaxbUtils {

	@SuppressWarnings("unchecked")
	public static <T> T readXmlFromFile(Class<T> clazz, String filePath)
			throws JAXBException {
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller u = jc.createUnmarshaller();
			return (T) u.unmarshal(new File(filePath));
		} catch (JAXBException e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T readConfig(Class<T> clazz, String configPath,
			Object... arguments) throws JAXBException, IOException {
		InputStream is = null;
		try {
			if (arguments.length > 0) {
				configPath = MessageFormat.format(configPath, arguments);
			}
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller u = jc.createUnmarshaller();
			is = new FileInputStream(configPath);
			return (T) u.unmarshal(is);
		} catch (IOException e) {
			throw e;
		} catch (JAXBException e) {
			throw e;
		} finally {
			try {
				if(is != null) is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T readConfigFromStream(Class<T> clazz,
			InputStream dataStream) throws JAXBException {
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller u = jc.createUnmarshaller();
			return (T) u.unmarshal(dataStream);
		} catch (JAXBException e) {
			throw e;
		} finally {
			try {
				if(dataStream != null) dataStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T readXMLFromString(Class<T> clazz, String xml) throws JAXBException, UnsupportedEncodingException {
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.getBytes(Global.MQ_MSG_CHARSET_NAME));
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller u = jc.createUnmarshaller();
			return (T) u.unmarshal(in);
		} catch (JAXBException e) {
			throw e;
		} finally {
			try {
				if(in != null) in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static <T> String write(Class<T> clazz, T obj) throws JAXBException {
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			JAXBContext context = JAXBContext.newInstance(clazz);  
	        Marshaller marshaller = context.createMarshaller();  
	        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
	        marshaller.setProperty(Marshaller.JAXB_ENCODING, Global.MQ_MSG_CHARSET_NAME);
	        marshaller.marshal(obj, out);  
	        return out.toString();
		} catch (JAXBException e) {
			throw e;
		} finally {
			try {
				if(out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws JAXBException, UnsupportedEncodingException {
		/*String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><res><header><msgId>0000207</msgId><hdlSts>B</hdlSts><bdFlg>1</bdFlg><rtnCd>E00000067</rtnCd></header><body></body></res>";
		
		BocOrderResult result = JaxbUtils.readConfigFromStream(BocOrderResult.class, new ByteArrayInputStream(xml.getBytes("UTF-8")));
        
		System.out.println();*/
		
		/*BocHeader header = new BocHeader("MCT001", "A", "0", "");
		BocReturnBankBody body = new BocReturnBankBody("111", "222", "333", "444", "555", "666", "777", "888");
		BocReturnBank res = new BocReturnBank(header, body);
		String xml = JaxbUtils.write(BocReturnBank.class, res);
		System.out.println(xml);*/
		
		/*String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><res><header><msgId>0000204</msgId><hdlSts>A</hdlSts><bdFlg>0</bdFlg><rtnCd /></header><body><orderTrans><merchantNo>333555</merchantNo><orderNo>1225</orderNo><orderSeq>12851</orderSeq><orderStatus>0</orderStatus><cardTyp>1</cardTyp><acctNo>5555510100123456789</acctNo><holderName>张三</holderName><ibknum>07428</ibknum><payTime>20110624171313</payTime><payAmount>14.00</payAmount><visitorIp>127.0.0.1</visitorIp><visitorRefer /></orderTrans><orderTrans><merchantNo>333555</merchantNo><orderNo>1225</orderNo><orderSeq>12851</orderSeq><orderStatus>0</orderStatus><cardTyp>1</cardTyp><acctNo>5555510100123456789</acctNo><holderName>张三</holderName><ibknum>07428</ibknum><payTime>20110624171313</payTime><payAmount>14.00</payAmount><visitorIp>127.0.0.1</visitorIp><visitorRefer /></orderTrans></body></res>";
		BocQueryResult result = JaxbUtils.readXMLFromString(BocQueryResult.class, xml);
		System.out.println();*/
		
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><alipay><is_success>T</is_success><response><trade><trade_no>我是1</trade_no><out_trade_no>我是2</out_trade_no><subject>我是3</subject><trade_status>我是4</trade_status></trade></response></alipay>";
		AlipayQueryResult result = JaxbUtils.readXMLFromString(AlipayQueryResult.class, xml);
		System.out.println();
		
	}
	
}
