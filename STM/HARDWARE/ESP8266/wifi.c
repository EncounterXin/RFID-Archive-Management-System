#include "wifi.h"
#include "bsp_dht11.h"
#include "glr_adc.h"     
extern __IO u16 data_value[6];
extern u32 AD1_value,hy,trq,yw;	  
/*
 * ATK-ESP8266���������,�����յ���Ӧ��
 * str:�ڴ���Ӧ����
 * ����ֵ:0,û�еõ��ڴ���Ӧ����
 *    ����,�ڴ�Ӧ������λ��(str��λ��)
 */
u8* esp8266_check_cmd( u8 *str )
{
	char *strx = 0;
	if ( USART2_RX_STA & 0X8000 )                           /* ���յ�һ�������� */
	{
		USART2_RX_BUF[USART2_RX_STA & 0X7FFF]	= 0;    /* ���ӽ����� */
//		printf("-\r\n---��ʼ-- ���յ���Ƭ�����أ�%s ---����--\r\n-\n\n",USART2_RX_BUF);
		strx	= strstr( (const char *) USART2_RX_BUF, (const char *) str );
//		printf("\r\n ��ѯ����strx��%c,%d,%s\r\n-\n\n",strx,strx,strx);
	}
	return( (u8 *) strx);
}


/*
 * ��ATK-ESP8266��������
 * cmd:���͵������ַ���
 * ack:�ڴ���Ӧ����,���Ϊ��,���ʾ����Ҫ�ȴ�Ӧ��
 * waittime:�ȴ�ʱ��(��λ:10ms)
 * ����ֵ:0,���ͳɹ�(�õ����ڴ���Ӧ����)
 *       1,����ʧ��
 */
u8 esp8266_send_cmd( u8 *cmd, u8 *ack, u16 waittime )
{
	u8 res = 0;
	USART2_RX_STA = 0;
	u2_printf( "%s\r\n", cmd );                     /* �������� */
	if ( ack && waittime )                          /* ��Ҫ�ȴ�Ӧ�� */
	{
		while ( --waittime )                    /* �ȴ�����ʱ */
		{
			delay_ms( 10 );
			if ( USART2_RX_STA & 0X8000 )   /* ���յ��ڴ���Ӧ���� */
			{
				if ( esp8266_check_cmd( ack ) )
				{
					printf( "ack:%s\r\n", (u8 *) ack );
					break;          /* �õ���Ч���� */
				}
				USART2_RX_STA = 0;
			}
		}
		if ( waittime == 0 ){
			res = 1;
			printf("��ʱ\n");
		}
	}
		printf("res:%d \n",res);
	return(res);
}


/*
 * ��ATK-ESP8266����ָ������
 * data:���͵�����(����Ҫ���ӻس���)
 * ack:�ڴ���Ӧ����,���Ϊ��,���ʾ����Ҫ�ȴ�Ӧ��
 * waittime:�ȴ�ʱ��(��λ:10ms)
 * ����ֵ:0,���ͳɹ�(�õ����ڴ���Ӧ����)luojian
 */
u8 esp8266_send_data( u8 *data, u8 *ack, u16 waittime )
{
	u8 res = 0;
	USART2_RX_STA = 0;
	u2_printf( "%s", data );                        /* �������� */
	if ( ack && waittime )                          /* ��Ҫ�ȴ�Ӧ�� */
	{
		while ( --waittime )                    /* �ȴ�����ʱ */
		{
			delay_ms( 10 );
			if ( USART2_RX_STA & 0X8000 )   /* ���յ��ڴ���Ӧ���� */
			{
				if ( esp8266_check_cmd( ack ) )
					break;          /* �õ���Ч���� */
				USART2_RX_STA = 0;
			}
		}
		if ( waittime == 0 ){
			res = 1;
			printf("��ʱ\n");
		}

	}
	
	printf("res:%d \n",res);
	return(res);
}


/*
 * ATK-ESP8266�˳�͸��ģʽ
 * ����ֵ:0,�˳��ɹ�;
 *       1,�˳�ʧ��
 */
u8 esp8266_quit_trans( void )
{
	while ( (USART2->SR & 0X40) == 0 )
		;                                       /* �ȴ����Ϳ� */
	USART2->DR = '+';
	delay_ms( 15 );                                 /* ���ڴ�����֡ʱ��(10ms) */
	while ( (USART2->SR & 0X40) == 0 )
		;                                       /* �ȴ����Ϳ� */
	USART2->DR = '+';
	delay_ms( 15 );                                 /* ���ڴ�����֡ʱ��(10ms) */
	while ( (USART2->SR & 0X40) == 0 )
		;                                       /* �ȴ����Ϳ� */
	USART2->DR = '+';
	delay_ms( 500 );                                /* �ȴ�500ms */
	return(esp8266_send_cmd( "AT", "OK", 20 ) );   /* �˳�͸���ж�. */
}




/*
 * ��ȡATK-ESP8266ģ�������״̬
 * ����ֵ:0,δ����;1,���ӳɹ�.
 */
u8 esp8266_consta_check( void )
{
	u8	*p;
	u8	res;
	if ( esp8266_quit_trans() )
		return(0);                              /* �˳�͸�� */
	esp8266_send_cmd( "AT+CIPSTATUS", ":", 50 );   /* ����AT+CIPSTATUSָ��,��ѯ����״̬ */
	p	= esp8266_check_cmd( "+CIPSTATUS:" );
	res	= *p;
	return(res);
}






/*
 * ��ȡAP+STA ip��ַ����ָ��λ����ʾ
 * ipbuf:ip��ַ���������
 */
void esp8266_get_ip( u8 x, u8 y )
{
	if ( esp8266_send_cmd( "AT+CIFSR", "OK", 50 ) ) /* ��ȡWAN IP��ַʧ�� */
	{
		printf( "IP��ȡʧ��\r\n" );
	}else {
		printf( "IP��ȡ�ɹ�\r\n" );
	}
}


/* ATK-ESP8266ģ����������� */
void ESP_INIT( void )
{
	u8 result = -1;
	/*
	 * while(esp8266_send_cmd("AT","OK",20));//���WIFIģ���Ƿ�����
	 * while(esp8266_send_cmd("ATE0","OK",20));//�رջ���
	 * //esp8266_msg_show(32,155,0);
	 */
//	printf("׼���˳�͸��\n");
//		while ( result = esp8266_send_cmd( "+++", "OK", 200 ) )           /* ���WIFIģ���Ƿ����� */
//	{
//		printf("AT���أ�%d \n",result);
//		printf( "�����˳�͸��!!!" );
//		delay_ms( 800 );
//	}
	
	u2_printf( "+++\r\n");  
	delay_ms( 800 );
		printf("׼������\n");
	
	while ( result = esp8266_send_cmd( "AT+RST", "OK", 00 ) )           /* ���WIFIģ���Ƿ����� */
	{
		printf("AT���أ�%d \n",result);
		printf( "��������!!!" );
		delay_ms( 800 );
	}
	delay_ms( 800 );
	
	
	printf( "WIFIģ�����\r\n" );
	
	
	//esp8266_send_cmd( "AT", "OK", 20 );

	
	while ( result = esp8266_send_cmd( "AT", "OK", 200 ) )           /* ���WIFIģ���Ƿ����� */
	{
		printf("AT���أ�%d \n",result);
		esp8266_quit_trans();                          /* �˳�͸�� */
		esp8266_send_cmd( "AT+CIPMODE=0", "OK", 200 ); /* �ر�͸��ģʽ */
		printf( "δ��⵽ģ��!!!" );
		delay_ms( 800 );
	}
	while ( esp8266_send_cmd( "ATE0", "OK", 200 ) );   /* �رջ��� */


	while ( esp8266_send_cmd( "AT+CWMODE=1", "OK", 200 ) );   /* �رջ��� */
	delay_ms( 10 );
  printf("׼������wifi\n");
	//while ( esp8266_send_cmd( "AT+CWJAP=\"Xiaomi 14\",\"Encounter@\"", "OK", 3000 ) )   /* ����wifi */
	while ( esp8266_send_cmd( "AT+CWJAP=\"lzxgy8412\",\"88888888\"", "OK", 3000 ) )
	{
		printf("��������wifi�����Ժ󡣡���\n");
		delay_ms( 1000 );
	}
  printf("����wifi�ɹ� \n");
	
	printf( "正在准备连接服务器,请稍等...\r\n" );

	//while ( esp8266_send_cmd( "AT+CIPSTART=\"TCP\",\"114.132.249.229\",9999", "OK", 3000 ) )
	//while ( esp8266_send_cmd( "AT+CIPSTART=\"TCP\",\"192.168.28.237\",9999", "OK", 3000 ) )
	while ( esp8266_send_cmd( "AT+CIPSTART=\"TCP\",\"server.rams.encounterdx.live\",80", "OK", 3000 ) )
	{
		printf( "正在测试是否连接服务器成功\r\n" );
	}

	printf("common112233\n");

	esp8266_get_ip( 15, 65 ); /* ��ȡ IP */


	//strcpy( p, "AT+CIPMODE=1" );
	printf( "���ڿ���͸��,���Ե�...\r\n" );
	while ( esp8266_send_cmd( "AT+CIPMODE=1", "OK", 200 ) )
	{
		printf( "���ڲ��Կ���͸���Ƿ�ɹ�\r\n" );

	}
	printf( "͸���ɹ�\r\n" );
	//strcpy( p, "AT+CIPSEND" );
	printf( "����׼����������,���Ե�...\r\n" );
	while ( esp8266_send_cmd( "AT+CIPSEND", "OK", 200 ) )
	{
		printf( "���ڲ���׼�����������Ƿ�ɹ�\r\n" );
	
	}
	printf( "׼���������ݳɹ�\r\n" );
//	//�����
//	GPIO_ResetBits(GPIOB,GPIO_Pin_8);	
	/*
	 * esp8266_at_response(1);//���ATK-ESP8266ģ�鷢�͹���������,��ʱ�ϴ�������
	 * esp8266_wifiap_test();	//WIFI AP����
	 */
//	esp8266_send_data_web();                           /* ap+sta���� */
	/* } */
}


