package ru.tfs.qr.qrservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="t_qr_code")
public class QrCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long			id;

	/** QR код вакцинации. Вместо него хэш по алгоритму MD5 */
	private String			code;

	@OneToOne(mappedBy = "qrCode", fetch = FetchType.LAZY)
	private VaccinationInfo	vaccinationInfo;

	public QrCode(String code) {
		this.code = code;
	}
}
