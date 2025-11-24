import csv
import faker
import random

fake = faker.Faker()

def deidentify_row(row):
    # De-identify each field only if it has data.
    # Given the example, we infer types/formats as:
    # Indexes:
    # 0 - ID: numeric string, generate 7 digit number as string
    # 1 - Description: text, generate fake sentence (short)
    # 2 - Code: numeric with decimal, generate random float with 2 decimals as string
    # 3 - Float: numeric float as string, generate float with 6 decimals string
    # 4 - Empty string: keep empty
    # 5 - Numeric string (likely int): generate random int string
    # 6 - Name or person: generate fake full name (uppercase)
    # 7-13 - multiple empty strings: keep
    # 14 - Code again (like index 2): numeric float string - generate float string 2 decimals
    # 15,16 - numeric string, probably int: generate random int string
    # 17 - Company name: generate fake company (uppercase)
    # 18 - Address line 1: fake street address (uppercase)
    # 19 - Address line 2: fake secondary address (uppercase)
    # 20 - Address line 3: empty string keep
    # 21 - City: fake city (uppercase)
    # 22 - State: fake state abbreviation (uppercase)
    # 23 - Zip: fake zipcode (5 digits, keep as string)
    # 24 - Empty string

    new_row = list(row)  # copy

    if new_row[0]:
        new_row[0] = str(random.randint(1000000, 9999999))

    if new_row[1]:
        # Make a short phrase mimicking description style
        new_row[1] = fake.sentence(nb_words=6).rstrip('.')

    if new_row[2]:
        new_row[2] = f"{random.uniform(900, 950):.2f}"

    if new_row[3]:
        new_row[3] = f"{random.uniform(0, 1):.6f}"

    # index 4 is empty, leave as is

    if new_row[5]:
        new_row[5] = str(random.randint(0, 10))

    if new_row[6]:
        # uppercase full name
        new_row[6] = fake.name().upper()

    # indexes 7-13 empty, leave as is

    if new_row[14]:
        new_row[14] = f"{random.uniform(900, 950):.2f}"

    if new_row[15]:
        new_row[15] = str(random.randint(0, 1000))

    if new_row[16]:
        new_row[16] = str(random.randint(0, 1000))

    if new_row[17]:
        new_row[17] = fake.company().upper()

    if new_row[18]:
        new_row[18] = fake.street_address().upper()

    if new_row[19]:
        new_row[19] = fake.secondary_address().upper()

    # index 20 empty keep

    if new_row[21]:
        new_row[21] = fake.city().upper()

    if new_row[22]:
        new_row[22] = fake.state_abbr().upper()

    if new_row[23]:
        new_row[23] = fake.zipcode_in_state(new_row[22]).split('-')[0]

    # 24 empty keep

    return new_row

def main():
    with open('From_500_To_Beyond_1_of_4.csv', newline='', encoding='utf-8') as infile, \
         open('output-1.csv', 'w', newline='', encoding='utf-8') as outfile:
        reader = csv.reader(infile)
        writer = csv.writer(outfile)

        header = next(reader)
        writer.writerow(header)

        for row in reader:
            new_row = deidentify_row(row)
            writer.writerow(new_row)

if __name__ == '__main__':
    main()
