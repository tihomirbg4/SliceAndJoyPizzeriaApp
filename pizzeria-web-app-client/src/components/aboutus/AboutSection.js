import React from "react";

const AboutSection = () => {
    return (
        <section id="about" className="bg-gray-100 py-8 text-center">
            <div className="container mx-auto">
                <div className="md:w-1/2 p-6 mx-auto flex flex-col items-center">
                    <h2 className="text-3xl font-semibold mb-4 font-courgette">
                        За нас
                    </h2>
                    <p className="text-gray-700 font-courgette">
                        Добре дошли в нашата пицария! Ние сме отдаден екип от
                        професионалисти, които горещо се стремим да доставяме
                        пиците ни с високо качество и изключителни клиентски
                        преживявания. Нашата мисия е да ви осигурим не само
                        храна, но и незабравими моменти, споделени с близки и
                        приятели. Очакваме ви в Slice&Joy за едно вкусно
                        преживяване, което оставя следа в сърцата и вкусовете на
                        всекиго от вас.
                    </p>
                </div>
            </div>
        </section>
    );
};

export default AboutSection;
