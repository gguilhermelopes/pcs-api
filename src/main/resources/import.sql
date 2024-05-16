INSERT INTO employee (id, name, img_url, cpf, email, address, phone, cellphone, emergency_contact, emergency_contact_phone, role) VALUES (UUID(), 'Nome 1', 'url1.jpg', '111.111.111-11', 'email1@example.com', 'Endereço 1', '1234567890', '987654321', 'Contato de Emergência 1', '987654321', 'Função 1');
INSERT INTO employee (id, name, img_url, cpf, email, address, phone, cellphone, emergency_contact, emergency_contact_phone, role) VALUES (UUID(), 'Nome 2', 'url2.jpg', '222.222.222-22', 'email2@example.com', 'Endereço 2', '0987654321', '123456789', 'Contato de Emergência 2', '123456789', 'Função 2');

INSERT INTO insurance (id, name, cnpj, contact, email, address, phone, cellphone) VALUES (UUID(), 'Seguro de Saúde 1', '11111111111111', 'Contato 1', 'email1@example.com', 'Endereço 1', '1234567890', '987654321');
INSERT INTO insurance (id, name, cnpj, contact, email, address, phone, cellphone) VALUES (UUID(), 'Seguro de Saúde 2', '22222222222222', 'Contato 2', 'email2@example.com', 'Endereço 2', '0987654321', '123456789');

INSERT INTO therapist (id, name, email, crp, address, phone, cellphone, expertise) VALUES (UUID(), 'Terapeuta 1', 'email1@example.com', 'CRP11111', 'Endereço 1', '1234567890', '987654321', 'Especialidade 1');
INSERT INTO therapist (id, name, email, crp, address, phone, cellphone, expertise) VALUES (UUID(), 'Terapeuta 2', 'email2@example.com', 'CRP22222', 'Endereço 2', '0987654321', '123456789', 'Especialidade 2');