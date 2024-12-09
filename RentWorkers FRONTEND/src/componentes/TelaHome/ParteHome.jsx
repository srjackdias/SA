
import '../TelaHome/parteHome.css';
import { Link } from 'react-router-dom';
import { FaFacebook, FaInstagram, FaTwitter } from 'react-icons/fa'; // Ícones para redes sociais

function ParteHome() {
    return (
        <div className="myPage homeContainer"> {/* Adicionado 'myPage' aqui */}
            {/* Navbar */}
            <div className="myPage navContainer"> {/* Adicionado 'myPage' aqui */}
                <div className="myPage logoDiv"> {/* Adicionado 'myPage' aqui */}
                    <img 
                        src="/images/LogoRentWorkers.png" 
                        alt="Logotipo RentWorkers" 
                        className="myPage logoImage" /> {/* Adicionado 'myPage' aqui */}
                </div>
                <div className='myPage buttonsNav'> {/* Adicionado 'myPage' aqui */}
                    <Link className='myPage buttonsHome' to="/login">Entrar</Link> {/* Adicionado 'myPage' aqui */}
                </div>
            </div>

            {/* Primeira Seção: Mensagem de Boas-Vindas */}
            <div className='myPage bodyContainer'> {/* Adicionado 'myPage' aqui */}
                <div className='myPage bodyMsg'> {/* Adicionado 'myPage' aqui */}
                    <div className='myPage bodyMensagemContainer'> {/* Adicionado 'myPage' aqui */}
                        <h2 className='myPage tituloMsgBody'>Bem-vindo à RentWorkers</h2> {/* Adicionado 'myPage' aqui */}
                        <p className='myPage descMsgBody'> {/* Adicionado 'myPage' aqui */}
                            Encontrar a ajuda ideal ficou mais simples. Conecte-se com profissionais qualificados, prontos para oferecer soluções eficientes e seguras, garantindo o cuidado e a confiança que você merece.
                        </p>
                    </div>
                </div>
                <div className='myPage divImagemHome'></div> {/* Adicionado 'myPage' aqui */}
            </div>

            {/* Divisor */}
            <div className="myPage divisor"> {/* Adicionado 'myPage' aqui */}
                <hr />
            </div>

            {/* Segunda Seção: Sobre Nós */}
            <div className="myPage sobreContainer"> {/* Adicionado 'myPage' aqui */}
                <div className="myPage sobreMsg"> {/* Adicionado 'myPage' aqui */}
                    <div className="myPage sobreMensagemContainer"> {/* Adicionado 'myPage' aqui */}
                        <h2 className="myPage tituloSobre">Sobre Nós</h2> {/* Adicionado 'myPage' aqui */}
                        <p className="myPage descSobre"> {/* Adicionado 'myPage' aqui */}
                            Na RentWorkers, acreditamos que todo lar merece cuidados excepcionais, e é por isso que conectamos pessoas a trabalhadores qualificados de forma simples e eficiente. Nossa missão é transformar a experiência de contratação de serviços em algo fácil e seguro.
                        </p>
                        <p className="myPage descSobre"> {/* Adicionado 'myPage' aqui */}
                            **Missão**: Oferecer aos nossos clientes uma plataforma confiável, eficiente e segura para contratar profissionais para serviços domésticos, mantendo sempre o compromisso com qualidade e respeito.
                        </p>
                        <p className="myPage descSobre"> {/* Adicionado 'myPage' aqui */}
                            **Visão**: Ser a maior plataforma de conexão entre clientes e profissionais do mercado, transformando a forma como os serviços domésticos são contratados e realizados.
                        </p>
                        <p className="myPage descSobre"> {/* Adicionado 'myPage' aqui */}
                            **Valores**: Confiança, qualidade, inovação, respeito e compromisso com a satisfação dos nossos clientes e trabalhadores.
                        </p>
                    </div>
                </div>
            </div>

            {/* Footer */}
            <footer className="myPage footerContainer"> {/* Adicionado 'myPage' aqui */}
                <div className="myPage footerContent"> {/* Adicionado 'myPage' aqui */}
                    <div className="myPage footerLogo"> {/* Adicionado 'myPage' aqui */}
                        <img src="/images/LogoRentWorkers.png" alt="Logo RentWorkers" />
                    </div>
                    <div className="myPage footerSocials"> {/* Adicionado 'myPage' aqui */}
                        <h3>Siga-nos</h3>
                        <div className="myPage socialIcons"> {/* Adicionado 'myPage' aqui */}
                            <FaFacebook className="myPage socialIcon" /> {/* Adicionado 'myPage' aqui */}
                            <FaInstagram className="myPage socialIcon" /> {/* Adicionado 'myPage' aqui */}
                            <FaTwitter className="myPage socialIcon" /> {/* Adicionado 'myPage' aqui */}
                        </div>
                    </div>
                </div>
                <div className="myPage footerBottom"> {/* Adicionado 'myPage' aqui */}
                    <p>© 2024 RentWorkers. Todos os direitos reservados.</p>
                </div>
            </footer>
        </div>
    );
}

export default ParteHome;
